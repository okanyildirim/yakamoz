package com.hof.yakamozauth.help.service;

import com.hof.yakamozauth.help.config.ExecutionContextService;
import com.hof.yakamozauth.help.service.model.*;
import com.hof.yakamozauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final ExecutionContextService executionContextService;

	public UserRegistrationResponse createUser(UserRegistrationRequest userRegistrationRequest) {
		executionContextService.create("auth-api", this.getClass().getSimpleName(), null, null, null, null, null);

		boolean isValidPassword = Pattern.compile("(?=^.{6,10}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$")
				.matcher(userRegistrationRequest.getPassword())
				.matches();
		if (!isValidPassword) {
			throw new AuthBusinessException(CmsApiErrorCodes.PASSWORD_IS_NOT_VALID);
		}
		UserAssembler userAssembler = new UserAssembler();
		User user = userAssembler.writeEntity(userRegistrationRequest);

		// Tenant tenant = tenantService.getTenantFromAnotherThread(userRegistrationRequest.getTenantCountryCode());
		executionContextService.getCurrent().setUsername(user.getEmail());
		executionContextService.getCurrent().setTenantIsoCode2(tenant.getCode());
		executionContextService.getCurrent().setTenantSchemaName(tenant.getSchemaName());

		if (logger.isDebugEnabled()) {
			logger.debug("Tenant country code: ".concat(userRegistrationRequest.getTenantCountryCode()));
			logger.debug("Tenant schema name: ".concat(tenant.getSchemaName()));
		}

		if (userRepository.existsByEmail(user.getEmail())) {
			throw new AuthBusinessException(CmsApiErrorCodes.EMAIL_ALREADY_EXIST_ERROR);
		}

		userRepository.save(user);
		String tenantCountryCode = executionContextService.getCurrent().getTenantIsoCode2();

		return userAssembler.writeRegistrationResponse(user, tenantCountryCode);
	}

	public List<UserRegistrationResponse> getAll() {
		List<UserRegistrationResponse> usersResponse = new ArrayList<>();
		Iterator<User> users = userRepository.findAll().iterator();
		UserAssembler userAssembler = new UserAssembler();
		String tenantCountryCode = executionContextService.getCurrent().getTenantIsoCode2();
		while (users.hasNext()) {
			User user = users.next();
			usersResponse.add(userAssembler.writeRegistrationResponse(user, tenantCountryCode));
		}
		return usersResponse;
	}

	public UserRegistrationResponse updateUser(UserRegistrationRequest userRegistrationRequest, Long id) {

		UserAssembler userAssembler = new UserAssembler();
		User user = userAssembler.writeEntity(userRegistrationRequest);
		String loggedInUser = executionContextService.getCurrent().getUsername();

		if (checkIfLoggedInUserIsAdmin(loggedInUser, user.getEmail(), user.getRoles())) {
			throw new AuthBusinessException(CmsApiErrorCodes.ADMIN_SELF_UPDATE_VALIDATION_ERROR);
		}

		User user2BeUpdated = userRepository.findById(id).orElseThrow(() -> new AuthBusinessException(CmsApiErrorCodes.NOT_FOUND_ERROR));

		if (!user2BeUpdated.getEmail().equals(user.getEmail())
				&& userRepository.existsByEmail(user.getEmail())) {
			throw new AuthBusinessException(CmsApiErrorCodes.EMAIL_ALREADY_EXIST_ERROR);
		}

		user.setId(id);
		userRepository.save(user);
		String tenantCountryCode = executionContextService.getCurrent().getTenantIsoCode2();

		return userAssembler.writeRegistrationResponse(user, tenantCountryCode);
	}

	public UserRegistrationResponse patchUser(UserUpdateRequest userRegistrationRequest, Long id) {
		User user2BeUpdated = userRepository.findById(id).orElseThrow(() -> new AuthBusinessException(CmsApiErrorCodes.NOT_FOUND_ERROR));
		String loggedInUser = executionContextService.getCurrent().getUsername();

		if (userRegistrationRequest.getEmail() != null && !user2BeUpdated.getEmail().equals(userRegistrationRequest.getEmail())
				&& userRepository.existsByEmail(userRegistrationRequest.getEmail())) {
			throw new AuthBusinessException(CmsApiErrorCodes.EMAIL_ALREADY_EXIST_ERROR);
		}

		if (checkIfLoggedInUserIsAdmin(loggedInUser, user2BeUpdated.getEmail(), user2BeUpdated.getRoles())) {
			throw new AuthBusinessException(CmsApiErrorCodes.ADMIN_SELF_UPDATE_VALIDATION_ERROR);
		}

		UserAssembler userAssembler = new UserAssembler();
		userAssembler.patchEntity(user2BeUpdated, userRegistrationRequest);

		userRepository.save(user2BeUpdated);
		String tenantCountryCode = executionContextService.getCurrent().getTenantIsoCode2();

		return userAssembler.writeRegistrationResponse(user2BeUpdated, tenantCountryCode);
	}

	public PasswordUpdateResponse updatePassword(PasswordUpdateRequest pword, Long id) {

		User user = userRepository.findById(id).orElseThrow(() -> new AuthBusinessException(CmsApiErrorCodes.NOT_FOUND_ERROR));
		String cryptedNewPword = BCrypt.hashpw(pword.getNewPassword(), BCrypt.gensalt());
		PasswordUpdateResponse response = new PasswordUpdateResponse();

		if (!pword.getNewPassword().equals(pword.getNewPasswordAgain()) ||
				!BCrypt.checkpw(pword.getOldPassword(), user.getPassword())) {
			throw new AuthBusinessException(CmsApiErrorCodes.PASSWORD_MISMATCH_ERROR);
		}

		if (!user.getPassword().equals(cryptedNewPword)) {
			user.setPassword(cryptedNewPword);
			userRepository.save(user);

			response.setOldPassword(pword.getOldPassword());
			response.setNewPassword(pword.getNewPassword());

			return response;
		} else {
			throw new AuthBusinessException(CmsApiErrorCodes.PASSWORD_CANNOT_BE_THE_SAME_ERROR);
		}
	}

	public void verifyUser(Long id) {

		String loggedInUser = executionContextService.getCurrent().getUsername();
		User user = userRepository.findById(id).orElseThrow(() -> new AuthBusinessException(CmsApiErrorCodes.NOT_FOUND_ERROR));

		if (!checkIfLoggedInUserIsAdmin(loggedInUser, user.getEmail(), user.getRoles())) {
			user.setIsValid(true);
		} else {
			throw new AuthBusinessException(CmsApiErrorCodes.ADMIN_SELF_UPDATE_VALIDATION_ERROR);
		}

		userRepository.save(user);
	}

	private boolean checkIfLoggedInUserIsAdmin(String firstUser, String secondUser, Set<UserRole> roleSet) {
		return firstUser.equals(secondUser) && roleSet.contains(UserRole.USER_ADMIN);
	}

	public void unverifyUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new AuthBusinessException(CmsApiErrorCodes.NOT_FOUND_ERROR));
		user.setIsValid(false);

		userRepository.save(user);
	}

	public UserRegistrationResponse getUser(Long id) {

		UserAssembler userAssembler = new UserAssembler();
		String tenantCountryCode = executionContextService.getCurrent().getTenantIsoCode2();
		User user = userRepository.findById(id).orElseThrow(() -> new AuthBusinessException(CmsApiErrorCodes.NOT_FOUND_ERROR));

		return userAssembler.writeRegistrationResponse(user, tenantCountryCode);
	}
}

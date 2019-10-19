package com.hof.yakamozauth.help.service;

import com.hof.yakamozauth.help.service.model.User;
import com.hof.yakamozauth.help.config.ExecutionContextService;
import com.hof.yakamozauth.help.service.model.TokenRequest;
import com.hof.yakamozauth.help.service.model.UserAssembler;
import com.hof.yakamozauth.help.service.model.UserRegistrationResponse;
import com.hof.yakamozauth.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, CmsUserDocument> redisTemplateForCmsUser;
    private final ExecutionContextService executionContextService;
    private final MonitoringService monitoringService;

    private static final String TOKEN_PREFIX = "token-";

    public String generateToken(TokenRequest tokenRequest) {
    	executionContextService.create("auth-api", this.getClass().getSimpleName(), null, null, null, null, null);
        UserAssembler userAssembler = new UserAssembler();
        String email = tokenRequest.getEmail();

        if (tokenRequest.getTenantCountryCode() != null && !tokenRequest.getTenantCountryCode().equals("")) {
            executionContextService.getCurrent().setUsername(email);
        } else {
            throw new AuthBusinessException(CmsApiErrorCodes.LOGIN_WITHOUT_TENANT_ERROR);
        }

        if (tokenRequest.getEmail() == null) {
            throw new AuthBusinessException(CmsApiErrorCodes.LOGIN_WITHOUT_EMAIL_ERROR);
        }

        User user = userRepository.findByEmail(email).orElseThrow(() -> new AuthBusinessException(CmsApiErrorCodes.USER_NOT_FOUND_ERROR));
        UserRegistrationResponse userResponse = userAssembler.writeRegistrationResponse(user, tokenRequest.getTenantCountryCode());

        validateRequestAndUser(tokenRequest, user);

        String token = UUID.randomUUID().toString();

        String activeToken = user.getActiveToken();
        if (activeToken != null) {
        	deleteCmsUser(activeToken);
        }

        user.setActiveToken(TOKEN_PREFIX + token);
        userRepository.save(user);

        storeCmsUser(token, userResponse.getCmsUser());

        return token;
    }

    private void validateRequestAndUser(TokenRequest tokenRequest, User user) {

        if (user.getValid() == null) {
            throw new AuthBusinessException(CmsApiErrorCodes.USER_NOT_VERIFIED_ERROR);
        }

        if (!user.getValid()) {
            throw new AuthBusinessException(CmsApiErrorCodes.REJECTED_USER_ERROR);
        }

        if (!BCrypt.checkpw(tokenRequest.getPassword(), user.getPassword())) {
            throw new AuthBusinessException(CmsApiErrorCodes.PASSWORD_MISMATCH_ERROR);
        }
    }

    public UserRegistrationResponse validate(String token) {
    	CmsUserDocument cmsUser = getCmsUser(token);
        if (cmsUser == null) {
            throw new AuthBusinessException(CmsApiErrorCodes.COULD_NOT_VALIDATE_TOKEN_ERROR);
        }

        return new UserRegistrationResponse(cmsUser);
    }

    public void invalidate(String token) {
    	deleteCmsUser(token);
    }
    
    public void storeCmsUser(String key, CmsUserDocument cmsUser) {
		this.redisTemplateForCmsUser.opsForValue().set(TOKEN_PREFIX + key, cmsUser, 1, TimeUnit.DAYS);
	}
    
	public CmsUserDocument getCmsUser(String key) {
		CmsUserDocument cmsUser = this.redisTemplateForCmsUser.opsForValue().get(TOKEN_PREFIX + key);

		if (cmsUser != null) {
			this.redisTemplateForCmsUser.expire(TOKEN_PREFIX + key, 1, TimeUnit.DAYS);
		}

		return cmsUser;
	}
	
	public void deleteCmsUser(String key) {
		this.redisTemplateForCmsUser.opsForValue().getOperations().delete(TOKEN_PREFIX + key);
	}
}

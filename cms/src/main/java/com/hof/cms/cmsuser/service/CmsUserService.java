package com.hof.cms.cmsuser.service;

import com.hof.cms.cmsuser.data.LoginRequest;
import com.hof.cms.cmsuser.data.CmsUserDto;
import com.hof.cms.cmsuser.data.CmsUserMapper;
import com.hof.cms.cmsuser.entity.CmsUser;
import com.hof.cms.cmsuser.repository.CmsUserRepository;
import com.hof.cms.common.exception.EmailAlreadyExistsException;
import com.hof.cms.common.exception.NotFoundException;
import com.hof.cms.common.exception.UserNameAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CmsUserService {

	private final CmsUserRepository cmsUserRepository;
	private final CmsUserMapper cmsUserMapper;

	public List<CmsUserDto> getAllUsers() {
		// TODO: 29.09.2019 should be pageable
		return cmsUserRepository.findAll().stream().map(cmsUserMapper::toUserDto).collect(Collectors.toList());
	}

	public void createUser(CmsUserDto request) {
		// UserValidator.createValidator(request); --> 2nd practice
		//request.userCreateRequestValidator();
		if (cmsUserRepository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExistsException(1000, "This email is already exists!");
		}

		if (cmsUserRepository.existsByUsername(request.getUsername())) {
			throw new UserNameAlreadyExistsException(1000, "This username is already exists!");
		}

		// TODO: REST best practice for create and update requests return objects
		CmsUser user = cmsUserMapper.toUser(request);
		cmsUserRepository.save(user);
	}

	public CmsUser editUser(CmsUserDto request) {
		// TODO: exception type can be changed and this sentence can be used as method for writing short instead of using much and long
		CmsUser cmsUser = cmsUserRepository.findById(request.getId()).orElseThrow(NullPointerException::new);

		// todo: analize göre baştan yazılması daha iyi olur email, password, username gibi alanların değişimine yönelik farklı endpintler daha iyi olur
		cmsUser.setUsername(request.getUsername());
		cmsUser.setPassword(request.getPassword());
		cmsUser.setEmail(request.getEmail());
		return cmsUserRepository.save(cmsUser);
	}

	public void deleteUser(Long id) {
		cmsUserRepository.deleteById(id);
	}

	public CmsUserDto getUser(Long id) {
		return cmsUserMapper.toUserDto(cmsUserRepository.findById(id).orElseThrow(() -> new NotFoundException(1000, "CmsUser is not found!")));
	}

	public Boolean login(LoginRequest loginRequest) {
		CmsUser cmsUser = cmsUserRepository.findByEmail(loginRequest.getEmail());
		if (cmsUser == null) {
			throw new NotFoundException(1000, "Email is not found!");
		}

		if (cmsUser.getPassword().equals(loginRequest.getPassword())) {
			return true;
		} else {
			return false;
		}
	}
}

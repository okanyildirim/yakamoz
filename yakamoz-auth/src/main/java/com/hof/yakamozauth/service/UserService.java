package com.hof.yakamozauth.service;

import com.hof.yakamozauth.common.exception.EmailAlreadyExistsException;
import com.hof.yakamozauth.common.exception.NotFoundException;
import com.hof.yakamozauth.common.exception.UserNameAlreadyExistsException;
import com.hof.yakamozauth.data.*;
import com.hof.yakamozauth.entity.User;
import com.hof.yakamozauth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public List<UserDto> getAllUsers() {
		// TODO: 29.09.2019 should be pageable
		return userRepository.findAll().stream().map(userMapper::toUserDto).collect(Collectors.toList());
	}

	public void createUser(UserDto request) {
		// UserValidator.createValidator(request); --> 2nd practice
		request.userCreateRequestValidator();
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExistsException(1000, "This email is already exists!");
		}

		if (userRepository.existsByUsername(request.getUsername())) {
			throw new UserNameAlreadyExistsException(1000, "This username is already exists!");
		}

		// TODO: REST best practice for create and update requests return objects
		User user = userMapper.toUser(request);
		userRepository.save(user);
	}

	public User editUser(UserDto request) {
		// TODO: exception type can be changed and this sentence can be used as method for writing short instead of using much and long
		User user = userRepository.findById(request.getId()).orElseThrow(NullPointerException::new);

		// todo: analize göre baştan yazılması daha iyi olur email, password, username gibi alanların değişimine yönelik farklı endpintler daha iyi olur
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		user.setEmail(request.getEmail());
		return userRepository.save(user);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public UserDto getUser(Long id) {
		return userMapper.toUserDto(userRepository.findById(id).orElseThrow(() -> new NotFoundException(1000, "User is not found!")));
	}

	public Boolean login(LoginRequest loginRequest) {
		User user = userRepository.findByEmail(loginRequest.getEmail());
		if (user == null) {
			throw new NotFoundException(1000, "Email is not found!");
		}

		if (user.getPassword().equals(loginRequest.getPassword())) {
			return true;
		} else {
			return false;
		}
	}
}

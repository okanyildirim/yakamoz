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


@Service
@AllArgsConstructor
public class UserService {

    private final  UserRepository userRepository;

    public List<UserResponse> getAllUsers(){

       Iterator<User> users = userRepository.findAll().iterator();
       List<UserResponse> usersResponse = new ArrayList<>();
       while (users.hasNext()){
           User user = users.next();
           usersResponse.add(UserMapper.userToUserResponse(user));
       }

        return  usersResponse;
    }

    public void createUser ( UserRegistrationRequest request){
        UserValidator.createValidator(request);
        if (userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(1000,"This email is already exists!");
        }

        if (userRepository.existsByUsername(request.getUsername())){
            throw new UserNameAlreadyExistsException(1000, "This username is already exists!");
        }
        User user = UserMapper.userRegistrationRequestToUser(request);
        userRepository.save(user);
    }

    public User editUser(UserRequest userRequest){
        User user = userRepository.findById(userRequest.getId()).orElseThrow(NullPointerException::new);

        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setUserDetails(userRequest.getUserDetails());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse getUser(Long id){
        return UserMapper.userToUserResponse(userRepository.findById(id).orElseThrow(() -> new NotFoundException(1000,"User is not found!")));
    }

    public Boolean login(LoginRequest loginRequest) {
        User user =  userRepository.findByEmail(loginRequest.getEmail());
        if (user == null){
            throw new NotFoundException(1000, "Email is not found!");
        }

        if (user.getPassword().equals(loginRequest.getPassword())){
            return true;
        } else {
            return false;
        }
    }
}

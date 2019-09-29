package com.hof.yakomozauth.data;

import com.hof.yakomozauth.entity.User;
import com.hof.yakomozauth.entity.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User userRequestToUser(UserRequest request) {

        if (request == null) {
            return null;
        }

        User user = new User();

        user.setId(request.getId());
        user.setUserDetails(request.getUserDetails());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRoles(request.getRoles());

        return user;
    }


    public static UserResponse userToUserResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setUserDetails(user.getUserDetails());
        userResponse.setUsername(user.getUsername());
        userResponse.setPassword(user.getPassword());
        userResponse.setRoles(user.getRoles());

        return userResponse;
    }

    public static User userRegistrationRequestToUser(UserRegistrationRequest request) {

        if (request == null) {
            return null;
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.addRole(UserRole.CLIENT);

        return user;
    }

    //request.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
}

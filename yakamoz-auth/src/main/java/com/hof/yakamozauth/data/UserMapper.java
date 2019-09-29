package com.hof.yakamozauth.data;

import com.hof.yakamozauth.entity.User;
import com.hof.yakamozauth.entity.UserRole;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserDetailsMapper userDetailsMapper;

    public User toUser(UserDto userDto) {

        if (userDto == null) {
            return null;
        }
        // todo builder
        User user = new User();
        user.setId(userDto.getId());
        user.setUserDetails(userDetailsMapper.toUserDetails(userDto.getUserDetails())); // todo içime sinmedi
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
        return user;
    }


    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUserDetails(userDetailsMapper.toUserDetailsDto(user.getUserDetails()));
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    //userDto.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
}

package com.hof.yakamozauth.data;

import com.hof.yakamozauth.entity.UserDetails;
import com.hof.yakamozauth.entity.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String password;
    private UserDetails userDetails;
    private Set<UserRole> roles;
}

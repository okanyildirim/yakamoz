package com.hof.yakamozauth.data;

import com.hof.yakamozauth.entity.UserDetails;
import com.hof.yakamozauth.entity.UserRole;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private Long id;
    private String username;
    private UserDetails userDetails;
    private String email;
    private String password;
    private Set<UserRole> roles;

}

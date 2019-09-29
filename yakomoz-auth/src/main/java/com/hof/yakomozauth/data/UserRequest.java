package com.hof.yakomozauth.data;

import com.hof.yakomozauth.entity.UserDetails;
import com.hof.yakomozauth.entity.UserRole;
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

package com.hof.yakomozauth.data;

import com.hof.yakomozauth.entity.UserDetails;
import com.hof.yakomozauth.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    private Long id;
    private String username;
    private String email;
    private String password;
    private UserDetails userDetails;
    private Set<UserRole> roles;
}

package com.hof.yakomozauth.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationRequest {
    private String username;
    private String email;
    private String password;
}

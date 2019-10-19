package com.hof.yakamozauth.help.service.model;

import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String addressLine;
    private String phoneNumber;
    private Set<UserRole> roles;
    private Boolean isValid;
}

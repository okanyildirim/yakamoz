package com.hof.yakamozauth.help.service.model;

import lombok.Data;

@Data
public class TokenRequest {

    private String email;
    private String password;
    private String tenantCountryCode;
}

package com.hof.yakomozauth.data;

import com.hof.yakomozauth.common.Utility;

public class UserValidator {

    public static void createValidator(UserRegistrationRequest request) {
        Utility.notNullAndLessThan("Firstname", request.getUsername(), 3, 50);
        String password = request.getPassword();
        Utility.notNullAndLessThan("Password", password, 7,50);
        Utility.validateMail(request.getEmail());
    }
}

package com.hof.yakamozauth.common.exception;

public class UserNameAlreadyExistsException extends AuthRunTimeException {

    public UserNameAlreadyExistsException(int errorCode, String message) {
        super(errorCode, message);
    }
}

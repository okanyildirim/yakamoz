package com.hof.yakamozauth.common.exception;

public class EmailAlreadyExistsException extends AuthRunTimeException {

    public EmailAlreadyExistsException(int errorCode, String message) {
        super(errorCode, message);
    }


}

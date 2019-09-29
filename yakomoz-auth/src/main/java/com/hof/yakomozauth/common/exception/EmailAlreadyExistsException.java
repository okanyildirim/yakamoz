package com.hof.yakomozauth.common.exception;

public class EmailAlreadyExistsException extends AuthRunTimeException {

    public EmailAlreadyExistsException(int errorCode, String message) {
        super(errorCode, message);
    }


}

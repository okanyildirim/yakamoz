package com.hof.yakomozauth.common.exception;

public class AuthBusinessException extends AuthRunTimeException {

    public AuthBusinessException(int errorCode, String message) {
        super(errorCode, message);
    }

}

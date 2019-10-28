package com.hof.cms.common.exception;

public class NotFoundException extends AuthRunTimeException {
    public NotFoundException(int errorCode, String message) {
        super(errorCode, message);
    }
}

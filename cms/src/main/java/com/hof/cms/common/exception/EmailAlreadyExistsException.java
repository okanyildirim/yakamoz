package com.hof.cms.common.exception;

public class EmailAlreadyExistsException extends AuthRunTimeException {

    public EmailAlreadyExistsException(int errorCode, String message) {
        super(errorCode, message);
    }


}

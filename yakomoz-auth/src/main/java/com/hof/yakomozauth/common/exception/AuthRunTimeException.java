package com.hof.yakomozauth.common.exception;

public class AuthRunTimeException extends RuntimeException {

    private int errorCode;

    public AuthRunTimeException(int errorCode) {
        this.errorCode = errorCode;
    }

    public AuthRunTimeException(String message) {
        super(message);
    }


    public AuthRunTimeException(int errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AuthRunTimeException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public AuthRunTimeException(Throwable cause, int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public AuthRunTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}

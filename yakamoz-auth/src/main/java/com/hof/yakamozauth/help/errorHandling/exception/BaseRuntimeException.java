package com.hof.yakamozauth.help.errorHandling.exception;


public abstract class BaseRuntimeException extends RuntimeException {

    private final transient ErrorCode errorCode;
    private String message;
    private Object[] params;

    public BaseRuntimeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BaseRuntimeException(ErrorCode errorCode, Throwable t) {
        super(t);
        this.errorCode = errorCode;
    }
    
    public BaseRuntimeException(ErrorCode errorCode, Object... params) {
        this.errorCode = errorCode;
        this.params = params;
    }

    public BaseRuntimeException(ErrorCode errorCode, Throwable t, Object... params) {
        super(t);
        this.errorCode = errorCode;
        this.params = params;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
    
    public String getMessage() {
        return message;
    }

    public Object[] getParams() {
        return params;
    }

}

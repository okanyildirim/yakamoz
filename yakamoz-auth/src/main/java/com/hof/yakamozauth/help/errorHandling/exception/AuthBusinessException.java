package com.hof.yakamozauth.help.errorHandling.exception;

public class AuthBusinessException extends BaseRuntimeException {

	public AuthBusinessException(ErrorCode errorCode) {
		super(errorCode);
	}

	public AuthBusinessException(ErrorCode errorCode, Throwable t) {
		super(errorCode, t);
	}

	public AuthBusinessException(ErrorCode errorCode, Object... params) {
		super(errorCode, params);
	}

	public AuthBusinessException(ErrorCode errorCode, String message, Throwable t) {
		super(errorCode, message, t);
	}
}
package com.hof.yakamozauth.help.errorHandling.exception;

public class YakamozAuthApiException extends BaseRuntimeException {

	public YakamozAuthApiException(ErrorCode errorCode) {
		super(errorCode);
	}

	public YakamozAuthApiException(ErrorCode errorCode, Throwable t) {
		super(errorCode, t);
	}

	public YakamozAuthApiException(ErrorCode errorCode, Throwable t, Object... params) {
		super(errorCode, t, params);
	}

	public YakamozAuthApiException(ErrorCode errorCode, Object... params) {
		super(errorCode, params);
	}

	public YakamozAuthApiException(ErrorCode errorCode, String message, Throwable t) {
		super(errorCode, message, t);
	}

}

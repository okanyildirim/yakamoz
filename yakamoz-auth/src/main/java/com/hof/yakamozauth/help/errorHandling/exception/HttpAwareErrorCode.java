package com.hof.yakamozauth.help.errorHandling.exception;

import org.springframework.http.HttpStatus;

public interface HttpAwareErrorCode extends ErrorCode {

	HttpStatus httpStatus();

}

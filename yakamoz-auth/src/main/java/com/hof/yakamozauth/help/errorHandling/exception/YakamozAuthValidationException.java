package com.hof.yakamozauth.help.errorHandling.exception;

import com.hof.yakamozauth.help.errorHandling.YakamozAuthApiErrorCodes;
import lombok.Data;

@Data
public class YakamozAuthValidationException extends BaseRuntimeException {

	private final ValidationResponse validationResponse;

	public YakamozAuthValidationException(ValidationResponse validationResponse) {
		super(YakamozAuthApiErrorCodes.VALIDATION_ERROR);
		this.validationResponse = validationResponse;
	}

}

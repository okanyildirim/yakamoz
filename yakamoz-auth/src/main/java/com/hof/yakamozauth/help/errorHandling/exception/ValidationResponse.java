package com.hof.yakamozauth.help.errorHandling.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationResponse {

	private List<String> errors = new ArrayList<>();

	public static ValidationResponse empty() {
		return new ValidationResponse();
	}

	public boolean isSuccess() {
		return errors.isEmpty();
	}

	public boolean hasErrors() {
		return ! errors.isEmpty();
	}

	public void addError(String message) {
		errors.add(message);
	}

}

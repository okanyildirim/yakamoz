package com.hof.yakamozauth.help.service.model;

import lombok.Data;

@Data
public class PasswordUpdateRequest {

	private String oldPassword;
	private String newPassword;
	private String newPasswordAgain;
}

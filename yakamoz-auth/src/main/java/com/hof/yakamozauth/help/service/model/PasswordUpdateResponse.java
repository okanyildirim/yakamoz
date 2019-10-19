package com.hof.yakamozauth.help.service.model;

import lombok.Data;

@Data
public class PasswordUpdateResponse {

	private String oldPassword;
	private String newPassword;
}

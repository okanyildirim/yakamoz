package com.hof.yakamozauth.help.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

	@Value("${auth.cookie.secure}")
	private String secure;

	public String getSecure() {
		return secure;
	}
}

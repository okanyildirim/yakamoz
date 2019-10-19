package com.hof.yakamozauth.help.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionContext {

	private String appId;

	private String serviceId;

	private String username;

	private String tenantIsoCode2;
	
	private String accessedCountryCode2;
	
	private String tenantSchemaName;

	private String clientIp;

	private String requestBody;

	private String responseBody;

	private Long transactionId;

	private String failureText;

	private Locale locale;
	
}

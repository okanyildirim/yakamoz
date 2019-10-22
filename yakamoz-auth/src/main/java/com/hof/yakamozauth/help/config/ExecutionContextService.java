package com.hof.yakamozauth.help.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class ExecutionContextService {

	private ThreadLocal<ExecutionContext> executionContextThreadLocal = new ThreadLocal<>();

	public ExecutionContext create(String appId, String serviceId, String username, String tenantIsoCode2,
			String tenantSchemaName, String accessedCountryCode2, String clientIp) {
		ExecutionContext executionContext = ExecutionContext.builder()
				.appId(appId)
				.serviceId(serviceId)
				.username(username)
				.tenantIsoCode2(tenantIsoCode2)
				.tenantSchemaName(tenantSchemaName)
				.accessedCountryCode2(accessedCountryCode2)
				.clientIp(clientIp)
				.transactionId((long) ((new SecureRandom()).nextDouble() * Long.MAX_VALUE))
				.build();

		executionContextThreadLocal.set(executionContext);
		return executionContext;
	}

	public void setCurrent(ExecutionContext executionContext) {
		executionContextThreadLocal.set(executionContext);
	}

	public void remove() {
		executionContextThreadLocal.remove();
	}

	public ExecutionContext getCurrent() {
		return executionContextThreadLocal.get();
	}

	public String getCurrentTenantCode() {
		return executionContextThreadLocal.get().getTenantIsoCode2();
	}

}

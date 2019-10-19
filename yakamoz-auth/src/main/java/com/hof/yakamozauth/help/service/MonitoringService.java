package com.hof.yakamozauth.help.service;

import com.hof.yakamozauth.help.config.ExecutionContext;
import com.hof.yakamozauth.help.config.ExecutionContextService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoringService {

	Logger applogger = LoggerFactory.getLogger("applogger");
	Logger trxlogger = LoggerFactory.getLogger("trxlogger");

	private final ExecutionContextService executionContextService;

	public void logTransaction() {
		ExecutionContext currentExecutionContext = getCurrentExecutionContext();
		if (currentExecutionContext == null) {
			return;
		}

		doLog(trxlogger, LogLevel.INFO, "Request-Body: [{}], Response-Body: [{}]", null, currentExecutionContext.getRequestBody(), currentExecutionContext.getResponseBody());
	}

	public void setFailureText(String format, Object... args) {
		setFailureText(MessageFormatter.format(format, args).getMessage());
	}

	public void setFailureText(String failureText) {
		getCurrentExecutionContext().setFailureText(failureText);
	}

	public String getFailureText() {
		return getCurrentExecutionContext().getFailureText();
	}

	public ExecutionContext getCurrentExecutionContext() {
		return executionContextService.getCurrent();
	}

	public void debug(String format, Object... args) {
		doLog(LogLevel.DEBUG, format, null, args);
	}

	public void info(String format, Object... args) {
		doLog(LogLevel.INFO, format, null, args);
	}

	public void warn(String format, Object... args) {
		doLog(LogLevel.WARN, format, null, args);
	}

	public void error(String format, Object... args) {
		doLog(LogLevel.ERROR, format, null, args);
	}

	public void error(String format, Exception ex, Object... args) {
		doLog(LogLevel.ERROR, format, ex, args);
	}

	private void doLog(LogLevel logLevel, String format, Exception ex, Object... args) {
		doLog(applogger, logLevel, format, ex, args);
	}

	private void doLog(Logger logger, LogLevel logLevel, String format, Exception ex, Object... args) {
		ExecutionContext executionContext = executionContextService.getCurrent();
		Long transactionId = executionContext == null ? null : executionContext.getTransactionId();
		String appId = executionContext == null ? null : executionContext.getAppId();
		String serviceId = executionContext == null ? null : executionContext.getServiceId();
		String userCountryIsoCode2 = executionContext == null ? null : executionContext.getAccessedCountryCode2();
		String username = executionContext == null ? null : executionContext.getUsername();
		String failureText = executionContext == null ? null : executionContext.getFailureText();

		String tenantSchemaName = executionContext == null ? null : executionContext.getTenantSchemaName();

		String clientIp = executionContext == null ? null : executionContext.getClientIp();

		String finalFormat = "trxid:{}, appId:{}, serviceId:{}, userCountryIsoCode2:{}, username:{}, tenant:{}, clientip:{}, failureText: {}, detailMessage: " + format;

		List<Object> finalArgsList = new ArrayList<>();
		finalArgsList.add(transactionId);
		finalArgsList.add(appId);
		finalArgsList.add(serviceId);
		finalArgsList.add(userCountryIsoCode2);
		finalArgsList.add(username);
		finalArgsList.add(tenantSchemaName);
		finalArgsList.add(clientIp);
		finalArgsList.add(failureText);

		if (args != null) {
			for (Object arg : args) {
				finalArgsList.add(arg);
			}
		}

		if (ex != null)
			finalArgsList.add(ex);

		Object[] finalArgs = finalArgsList.toArray();

		if (logLevel == LogLevel.DEBUG) {
			logger.debug(finalFormat, finalArgs);
		} else if (logLevel == LogLevel.INFO) {
			logger.info(finalFormat, finalArgs);
		} else if (logLevel == LogLevel.WARN) {
			logger.warn(finalFormat, finalArgs);
		} else if (logLevel == LogLevel.ERROR) {
			logger.error(finalFormat, finalArgs);
		}

	}

	public void endCurrentExecutionContext() {
		executionContextService.remove();
	}

	public void createNewExecutionContext(String appId, String serviceId, String userName, String tenantIsoCode2,
			String tenantSchemaName, String clientIp) {
		executionContextService.create(appId, serviceId, userName, tenantIsoCode2, tenantSchemaName, null, clientIp);
	}

}

package com.hof.yakamozauth.help.errorHandling;

import com.hof.yakamozauth.help.config.ExecutionContextService;
import com.hof.yakamozauth.help.errorHandling.exception.BaseRuntimeException;
import com.hof.yakamozauth.help.errorHandling.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageTextResolver {

	private final ExecutionContextService executionContextService;

	private List<MessageSource> registeredMessageSourceList = new ArrayList<>();

	public void registerMessageSource(MessageSource messageSource) {
		this.registeredMessageSourceList.add(messageSource);
	}

	public String resolve(ErrorCode errorCode, Object... args) {
		return resolve(errorCode, executionContextService.getCurrent().getLocale(), args);
	}

	public String resolve(ErrorCode errorCode, Locale locale, Object... args) {
		String key = errorCode.prefix() + "-" + errorCode.code();
		String resolvedMessageText = resolve(key, args, locale);

		if (resolvedMessageText == null)
			resolvedMessageText = errorCode.code().toString();

		return resolvedMessageText;
	}

	public String resolve(BaseRuntimeException bre, Locale locale) {
		if (bre.getErrorCode() == null) {
			return bre.getMessage();
		}

		String key = bre.getErrorCode().prefix() + "-" + bre.getErrorCode().code();
		String resolvedMessageText = resolve(key, bre.getParams(), locale);

		if (resolvedMessageText == null) {
			resolvedMessageText = bre.getMessage();
		}

		return resolvedMessageText;
	}

	public String resolve(String key) {
		return resolve(key, null, executionContextService.getCurrent().getLocale());
	}

	public String resolve(String key, Object... args) {
		return resolve(key, args, executionContextService.getCurrent().getLocale());
	}


	public String resolve(String key, Locale locale) {
		return resolve(key, null, locale);
	}

	public String resolve(String key, Object[] args, Locale locale) {
		String resolvedMessageText = null;
		for (MessageSource messageSource : registeredMessageSourceList) {
			try {
				String message = messageSource.getMessage(key, args, locale);
				if (! key.equals(message)) {
					resolvedMessageText = message;
				}
			} catch (NoSuchMessageException nsme) {
				// do nothing, try next message source
			}

			// break if we find the message text from this source
			if (resolvedMessageText != null) {
				break;
			}
		}

		// use the key if no message is found
		if (resolvedMessageText == null) {
			resolvedMessageText = key;
		}

		return resolvedMessageText;
	}
}

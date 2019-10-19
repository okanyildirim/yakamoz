package com.hof.yakamozauth.help.errorHandling;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class YakamozAuthApiI18nMessageSourceConfig {

	private final MessageTextResolver messageTextResolver;

	/**
	 * the loaded messageSource should be registered to messageTextResolver
	 */
	@PostConstruct
	public void init() {
		messageTextResolver.registerMessageSource(messageSource());
	}

	public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:/messages/cms-api-messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(5);
		return messageSource;
	}


}
package com.hof.yakamozauth.help.audit;

import com.hof.yakamozauth.help.config.ExecutionContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorAware")
@RequiredArgsConstructor
public class JPAAuditConfig {

	private final ExecutionContextService executionContextService;
	
	@Bean
	AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
	
	class AuditorAwareImpl implements AuditorAware<String> {

		@Override
		public Optional<String> getCurrentAuditor() {
			return Optional.ofNullable(executionContextService.getCurrent().getUsername());
		}
		
	}
	
}

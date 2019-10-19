package com.hof.yakamozauth.help.config;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final TokenService tokenService;
	private final TenantService tenantService;
	private final ExecutionContextService executionContextService;
	private final SessionLocaleResolver sessionLocaleResolver;
	private final MonitoringService monitoringService;

    @Override
    public void configure(WebSecurity web) {
        // Overridden to exclude some url's
        web.ignoring().antMatchers("/tokens/**").antMatchers("/captcha/**");
    }
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf().disable()
				.logout().disable()
				.formLogin().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.anonymous()
				.and()
				.exceptionHandling().authenticationEntryPoint(
				(req, rsp, e) -> {
					rsp.addHeader("Access-Control-Allow-Origin", "*");
					rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				})
				.and()
				.addFilterAfter(new TokenAuthenticationFilter(tenantService, executionContextService, 
						monitoringService, tokenService, sessionLocaleResolver), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers("/swagger-ui.html").permitAll()
				.antMatchers("/v2/api-docs").permitAll()
				.antMatchers("/tokens/*").permitAll()
				.antMatchers(HttpMethod.POST, "/users/").permitAll()
				.antMatchers(HttpMethod.GET, "/users/").hasAuthority(UserRole.USER_ADMIN.name())
				.antMatchers("/users/change-password/**").permitAll()
				.antMatchers("/users/getuser/**").permitAll()
				.antMatchers("/users/**").hasAuthority(UserRole.USER_ADMIN.name())
                .antMatchers("/channels/**").hasAuthority(UserRole.LTV_OPERATIONS.name())
				.antMatchers(HttpMethod.PUT, "/contents/*/play-urls").permitAll()
				.anyRequest().authenticated();
	}
}

package com.hof.yakamozauth.help.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final ExecutionContextService executionContextService;
	private final MonitoringService monitoringService;
	private final TokenService tokenService;

	private final SessionLocaleResolver sessionLocaleResolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			executionContextService.create("cms-api", request.getRequestURI(), null, null, null, null, null);
			Cookie[] cookies = request.getCookies();

			Optional<Cookie> cookie = Arrays.stream(cookies).filter(c -> c.getName().equals("token")).findFirst();

			if (!cookie.isPresent()) {
				chain.doFilter(request, response);
				return;
			}

			String token = cookie.get().getValue();
			CmsUserDocument cmsUserDocument = tokenService.getCmsUser(token);
			if (cmsUserDocument == null) {
				throw new CmsBusinessException("Authentication validation has failed. Could not find token in Redis");
			}

			executionContextService.getCurrent().setUsername(cmsUserDocument.getEmail());

			monitoringService.debug("setting SCHEMA_TENANT: {}", tenant.getSchemaName());
			executionContextService.getCurrent().setTenantIsoCode2(tenant.getCode());
			executionContextService.getCurrent().setTenantSchemaName(tenant.getSchemaName());

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(cmsUserDocument.getId(), null,
					cmsUserDocument.getRoles().stream().map(a -> new SimpleGrantedAuthority(a.name())).collect(Collectors.toList()));
			authToken.setDetails(new UserRegistrationResponse(cmsUserDocument));
			SecurityContextHolder.getContext().setAuthentication(authToken);

			Locale locale = StringUtils.parseLocaleString("tr");
			sessionLocaleResolver.setLocale(request, response, locale);
			executionContextService.getCurrent().setLocale(locale);

			chain.doFilter(request, response);
		} catch (HttpClientErrorException.NotFound e) {
			monitoringService.error("Authentication error [{}], msg: [{}]", e, e.getClass().getName(), e.getMessage());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		} catch (Exception e) {
			monitoringService.error("Authentication error [{}], msg: [{}]", e, e.getClass().getName(), e.getMessage());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}

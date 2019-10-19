package com.hof.yakamozauth.help.interceptor;

import com.hof.yakamozauth.help.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * Allows logging outgoing requests and the corresponding responses. Requires
 * the use of a  {@link org.springframework.http.client.BufferingClientHttpRequestFactory} to log the body of received responses.
 */
@Component
@RequiredArgsConstructor
public class RestClientLoggingInterceptor implements ClientHttpRequestInterceptor {

	private final MonitoringService monitoringService;

	/*
	 * Used to guarantee that missing BufferingClientHttpResponseWrapper log is made only once.
	 */
	private volatile boolean loggedMissingBuffering;

	private static final String PSSWRD_EXCLUDE_PATTERN = "(?<=\"(?i)p(?>assword|wd)\":\")[^\"]++";

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		logRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		logResponse(request, response);
		return response;
	}

	protected void logRequest(HttpRequest request, byte[] body) {
		StringBuilder builder = new StringBuilder("Sending ")
				.append(request.getMethod())
				.append(" request to ")
				.append(request.getURI());

		if (body.length > 0 && hasTextBody(request.getHeaders())) {
			String bodyText = new String(body, determineCharset(request.getHeaders()));
			bodyText = bodyText.replaceAll(PSSWRD_EXCLUDE_PATTERN, "XXXXXX");

			builder.append(": [ RequestBody: ").append(bodyText).append("]");
		}

		monitoringService.info(builder.toString());
	}

	protected void logResponse(HttpRequest request, ClientHttpResponse response) {
		try {

			StringBuilder builder = new StringBuilder("Received \"")
					.append(response.getRawStatusCode())
					.append(" ")
					.append(response.getStatusText())
					.append("\" response for ")
					.append(request.getMethod())
					.append(" from ")
					.append(request.getURI());

			HttpHeaders responseHeaders = response.getHeaders();
			long contentLength = responseHeaders.getContentLength();
			if (contentLength != 0) {
				if (hasTextBody(responseHeaders) && isBuffered(response)) {
					String bodyText = StreamUtils.copyToString(response.getBody(), determineCharset(responseHeaders));
					builder.append(": ResponseBody: [").append(bodyText).append("]");
				} else {
					if (contentLength == -1) {
						builder.append(" with content of unknown length");
					} else {
						builder.append(" with content of length ").append(contentLength);
					}
					MediaType contentType = responseHeaders.getContentType();
					if (contentType != null) {
						builder.append(" and content type ").append(contentType);
					} else {
						builder.append(" and unknown context type");
					}
				}
			}

			monitoringService.info(builder.toString());
		} catch (IOException e) {
			monitoringService.warn("Failed to log response for {} request to {}", request.getMethod(), request.getURI(), e);
		}
	}

	protected boolean hasTextBody(HttpHeaders headers) {
		MediaType contentType = headers.getContentType();
		if (contentType != null) {
			String subtype = contentType.getSubtype();
			return "text".equals(contentType.getType()) || "xml".equals(subtype) || "json".equals(subtype)
					|| "x-www-form-urlencoded".equals(subtype);
		}
		return false;
	}

	protected Charset determineCharset(HttpHeaders headers) {
		MediaType contentType = headers.getContentType();
		if (contentType != null) {
			try {
				Charset charSet = contentType.getCharset();
				if (charSet != null) {
					return charSet;
				}
			} catch (UnsupportedCharsetException e) {
				// ignore
			}
		}
		return StandardCharsets.UTF_8;
	}

	protected boolean isBuffered(ClientHttpResponse response) {
		// class is non-public, so we check by name
		boolean buffered = "org.springframework.http.client.BufferingClientHttpResponseWrapper".equals(response.getClass().getName());

		if (!buffered && !loggedMissingBuffering) {
			monitoringService.warn("Can't log HTTP response bodies, as you haven't configured the RestTemplate with a BufferingClientHttpRequestFactory");
			loggedMissingBuffering = true;
		}
		return buffered;
	}
}
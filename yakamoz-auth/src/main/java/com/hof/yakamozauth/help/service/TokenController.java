package com.hof.yakamozauth.help.service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TokenController {

    private final TokenService tokenService;
    private final AuthConfig authConfig;

    @PostMapping("/")
    public ApiResponse<TokenResponse> generateToken(@RequestBody TokenRequest tokenRequest, HttpServletResponse servletResponse) {
        String token = tokenService.generateToken(tokenRequest);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        if (authConfig.getSecure() == null || authConfig.getSecure().equals("true")) {
            cookie.setSecure(true);
        }
        cookie.setMaxAge(60 * 59 * 24);
        cookie.setPath("/");
        servletResponse.addCookie(cookie);
        return ApiResponse.of(tokenResponse);
    }

    @GetMapping("/{token}")
    public ApiResponse<UserRegistrationResponse> isValid(@PathVariable String token) {
	    UserRegistrationResponse response = tokenService.validate(token);
	    return ApiResponse.of(response);
    }

    @DeleteMapping("/invalidate")
    public ApiResponse<Void> invalidate(HttpServletResponse servletResponse, HttpServletRequest servletRequest) {
        Cookie[] cookies = servletRequest.getCookies();

        if (cookies == null) {
            return ApiResponse.empty();
        }

        Optional<Cookie> requestCookie = Arrays.stream(cookies).filter(c -> c.getName().equals("token")).findFirst();

        requestCookie.ifPresent(cookie -> tokenService.invalidate(cookie.getValue()));

        Cookie cookie = new Cookie("token", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        if (authConfig.getSecure() == null || authConfig.getSecure().equals("true")) {
            cookie.setSecure(true);
        }
        servletResponse.addCookie(cookie);
        return ApiResponse.empty();
    }
}
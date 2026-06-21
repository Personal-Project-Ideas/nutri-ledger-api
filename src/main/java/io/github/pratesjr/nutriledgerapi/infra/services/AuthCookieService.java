package io.github.pratesjr.nutriledgerapi.infra.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;

import io.github.pratesjr.nutriledgerapi.application.ports.AuthCookieServicePort;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

@Service
public class AuthCookieService implements AuthCookieServicePort {
    public static final String AUTH_COOKIE_NAME = "AUTH_TOKEN";
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24; // 1 day
    private static final String COOKIE_PATH = "/";
    private static final String COOKIE_SAME_SITE = "Strict";

    private final boolean cookieSecure;
    private final boolean cookieHttpOnly;

    public AuthCookieService(
            @Value("${app.auth.cookie.secure}") boolean cookieSecure,
            @Value("${app.auth.cookie.http-only:true}") boolean cookieHttpOnly
    ) {
        this.cookieSecure = cookieSecure;
        this.cookieHttpOnly = cookieHttpOnly;
    }

    @Override
    public void addAuthCookie(HttpServletResponse response, String token) {
        String safeToken = Objects.requireNonNull(token, "token");
        writeAuthCookie(response, safeToken, COOKIE_MAX_AGE);
    }

    @Override
    public void removeAuthCookie(HttpServletResponse response) {
        writeAuthCookie(response, "", 0);
    }

    private void writeAuthCookie(HttpServletResponse response, String value, int maxAge) {
        String safeValue = Objects.requireNonNull(value, "value");
        ResponseCookie cookie = ResponseCookie.from(AUTH_COOKIE_NAME, safeValue)
                .httpOnly(cookieHttpOnly)
                .secure(cookieSecure)
                .path(COOKIE_PATH)
                .maxAge(maxAge)
                .sameSite(COOKIE_SAME_SITE)
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
    }
}

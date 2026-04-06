package io.github.pratesjr.nutriledgerapi.infra.services;

import io.github.pratesjr.nutriledgerapi.infra.services.AuthCookieServicePort;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthCookieService implements AuthCookieServicePort {
    public static final String AUTH_COOKIE_NAME = "AUTH_TOKEN";
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24; // 1 day

    @Autowired
    public AuthCookieService() {
    }

    @Override
    public void addAuthCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(AUTH_COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_MAX_AGE);
        response.addCookie(cookie);
        response.setHeader("Set-Cookie",
            String.format("%s=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=Strict", AUTH_COOKIE_NAME, token, COOKIE_MAX_AGE)
        );
    }
}

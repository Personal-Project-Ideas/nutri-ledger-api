package io.github.pratesjr.nutriledgerapi.application.ports;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthCookieServicePort {
    void addAuthCookie(HttpServletResponse response, String token);
}

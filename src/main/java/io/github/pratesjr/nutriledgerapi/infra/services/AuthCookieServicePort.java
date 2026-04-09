package io.github.pratesjr.nutriledgerapi.infra.services;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthCookieServicePort {
    void addAuthCookie(HttpServletResponse response, String token);
}

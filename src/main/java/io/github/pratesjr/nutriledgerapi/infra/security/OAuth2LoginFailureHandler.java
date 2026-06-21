package io.github.pratesjr.nutriledgerapi.infra.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {

    private final String loginFailureRedirect;

    public OAuth2LoginFailureHandler(
            @Value("${app.oauth2.login-failure-redirect}") String loginFailureRedirect
    ) {
        this.loginFailureRedirect = loginFailureRedirect;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        String sep = loginFailureRedirect.contains("?") ? "&" : "?";
        String reason = URLEncoder.encode("oauth2", StandardCharsets.UTF_8);
        response.sendRedirect(loginFailureRedirect + sep + "error=" + reason);
    }
}

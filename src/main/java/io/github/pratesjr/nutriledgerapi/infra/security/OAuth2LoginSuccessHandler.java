package io.github.pratesjr.nutriledgerapi.infra.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.github.pratesjr.nutriledgerapi.application.dtos.OAuthUserDto;
import io.github.pratesjr.nutriledgerapi.application.ports.AuthCookieServicePort;
import io.github.pratesjr.nutriledgerapi.application.ports.AuthUserUseCasePort;
import io.github.pratesjr.nutriledgerapi.application.ports.CreateUserUseCasePort;
import io.github.pratesjr.nutriledgerapi.domain.errors.UserNotAllowedToBeCreatedException;
import io.github.pratesjr.nutriledgerapi.domain.errors.UserNotFoundException;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthUserUseCasePort authUserUseCase;
    private final CreateUserUseCasePort createUserUseCase;
    private final AuthCookieServicePort authCookieService;
    private final String loginSuccessRedirect;
    private final String loginFailureRedirect;

    public OAuth2LoginSuccessHandler(
            AuthUserUseCasePort authUserUseCase,
            CreateUserUseCasePort createUserUseCase,
            AuthCookieServicePort authCookieService,
            @Value("${app.oauth2.login-success-redirect}") String loginSuccessRedirect,
            @Value("${app.oauth2.login-failure-redirect}") String loginFailureRedirect
    ) {
        this.authUserUseCase = authUserUseCase;
        this.createUserUseCase = createUserUseCase;
        this.authCookieService = authCookieService;
        this.loginSuccessRedirect = loginSuccessRedirect;
        this.loginFailureRedirect = loginFailureRedirect;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            sendRedirectFailure(response, "invalid_auth");
            return;
        }
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        if (!(oauth2User instanceof GoogleOAuthPrincipal principal)) {
            sendRedirectFailure(response, "invalid_principal");
            return;
        }

        OAuthUserDto dto = principal.oauthUserDto();
        try {
            issueCookieAndRedirect(response, dto);
        } catch (UserNotFoundException e) {
            try {
                AllowedUser allowed = new AllowedUser(dto.getEmail());
                createUserUseCase.process(allowed, dto);
                issueCookieAndRedirect(response, dto);
            } catch (UserNotAllowedToBeCreatedException ex) {
                sendRedirectFailure(response, "not_allowed");
            }
        }
    }

    private void issueCookieAndRedirect(HttpServletResponse response, OAuthUserDto dto) throws IOException {
        String token = authUserUseCase.authenticate(dto).getToken();
        authCookieService.addAuthCookie(response, token);
        response.sendRedirect(loginSuccessRedirect);
    }

    private void sendRedirectFailure(HttpServletResponse response, String reason) throws IOException {
        String sep = loginFailureRedirect.contains("?") ? "&" : "?";
        String url = loginFailureRedirect + sep + "error=" + URLEncoder.encode(reason, StandardCharsets.UTF_8);
        response.sendRedirect(url);
    }
}

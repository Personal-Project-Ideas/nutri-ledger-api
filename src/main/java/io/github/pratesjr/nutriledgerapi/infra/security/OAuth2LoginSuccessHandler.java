package io.github.pratesjr.nutriledgerapi.infra.security;

import java.io.IOException;

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
import io.github.pratesjr.nutriledgerapi.http.handlers.ApiErrorResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final String SUCCESS_BODY = "{\"message\":\"Login successful\"}";

    private final AuthUserUseCasePort authUserUseCase;
    private final CreateUserUseCasePort createUserUseCase;
    private final AuthCookieServicePort authCookieService;
    private final ApiErrorResponseWriter apiErrorResponseWriter;

    public OAuth2LoginSuccessHandler(
            AuthUserUseCasePort authUserUseCase,
            CreateUserUseCasePort createUserUseCase,
            AuthCookieServicePort authCookieService,
            ApiErrorResponseWriter apiErrorResponseWriter
    ) {
        this.authUserUseCase = authUserUseCase;
        this.createUserUseCase = createUserUseCase;
        this.authCookieService = authCookieService;
        this.apiErrorResponseWriter = apiErrorResponseWriter;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            apiErrorResponseWriter.write(request, response, "http_401_001");
            return;
        }
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        if (!(oauth2User instanceof GoogleOAuthPrincipal principal)) {
            apiErrorResponseWriter.write(request, response, "http_401_001");
            return;
        }

        OAuthUserDto dto = principal.oauthUserDto();
        try {
            issueAuthResponse(response, dto);
        } catch (UserNotFoundException e) {
            try {
                createUserUseCase.process(new AllowedUser(dto.email()), dto);
                issueAuthResponse(response, dto);
            } catch (UserNotAllowedToBeCreatedException ex) {
                apiErrorResponseWriter.write(request, response, UserNotAllowedToBeCreatedException.CODE);
            }
        }
    }

    private void issueAuthResponse(HttpServletResponse response, OAuthUserDto dto) throws IOException {
        String token = authUserUseCase.authenticate(dto).token();
        authCookieService.addAuthCookie(response, token);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(SUCCESS_BODY);
    }
}

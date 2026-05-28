package io.github.pratesjr.nutriledgerapi.infra.security;

import io.github.pratesjr.nutriledgerapi.application.dtos.OAuthUserDto;
import io.github.pratesjr.nutriledgerapi.application.ports.AuthCookieServicePort;
import io.github.pratesjr.nutriledgerapi.application.ports.AuthUserUseCasePort;
import io.github.pratesjr.nutriledgerapi.application.ports.CreateUserUseCasePort;
import io.github.pratesjr.nutriledgerapi.domain.errors.UserNotAllowedToBeCreatedException;
import io.github.pratesjr.nutriledgerapi.domain.errors.UserNotFoundException;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.http.dtos.AuthResultDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OAuth2LoginSuccessHandlerTest {

    private static final String SUCCESS_URL = "http://localhost:3000/";
    private static final String FAILURE_URL = "http://localhost:3000/login";

    @Mock
    private AuthUserUseCasePort authUserUseCase;

    @Mock
    private CreateUserUseCasePort createUserUseCase;

    @Mock
    private AuthCookieServicePort authCookieService;

    @Test
    void shouldIssueCookieAndRedirectWhenUserExists() throws Exception {
        OAuthUserDto dto = new OAuthUserDto("user@example.com", "User", null);
        OAuth2AuthenticationToken authentication = oauthToken(dto);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(authUserUseCase.authenticate(dto)).thenReturn(new AuthResultDto("jwt-token"));

        handler().onAuthenticationSuccess(mock(HttpServletRequest.class), response, authentication);

        verify(authCookieService).addAuthCookie(response, "jwt-token");
        verify(response).sendRedirect(SUCCESS_URL);
    }

    @Test
    void shouldCreateAllowedUserWhenNotFoundThenIssueCookie() throws Exception {
        OAuthUserDto dto = new OAuthUserDto("new@example.com", "New User", null);
        OAuth2AuthenticationToken authentication = oauthToken(dto);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(authUserUseCase.authenticate(dto))
                .thenThrow(new UserNotFoundException("new@example.com"))
                .thenReturn(new AuthResultDto("jwt-token"));
        when(createUserUseCase.process(any(AllowedUser.class), eq(dto))).thenReturn(null);

        handler().onAuthenticationSuccess(mock(HttpServletRequest.class), response, authentication);

        ArgumentCaptor<AllowedUser> allowedCaptor = ArgumentCaptor.forClass(AllowedUser.class);
        verify(createUserUseCase).process(allowedCaptor.capture(), eq(dto));
        assertEquals("new@example.com", allowedCaptor.getValue().getEmail());
        verify(authCookieService).addAuthCookie(response, "jwt-token");
        verify(response).sendRedirect(SUCCESS_URL);
    }

    @Test
    void shouldRedirectToFailureWhenUserIsNotAllowed() throws Exception {
        OAuthUserDto dto = new OAuthUserDto("blocked@example.com", "Blocked", null);
        OAuth2AuthenticationToken authentication = oauthToken(dto);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(authUserUseCase.authenticate(dto)).thenThrow(new UserNotFoundException("blocked@example.com"));
        when(createUserUseCase.process(any(AllowedUser.class), eq(dto)))
                .thenThrow(new UserNotAllowedToBeCreatedException("blocked@example.com"));

        handler().onAuthenticationSuccess(mock(HttpServletRequest.class), response, authentication);

        ArgumentCaptor<String> redirectCaptor = ArgumentCaptor.forClass(String.class);
        verify(response).sendRedirect(redirectCaptor.capture());
        assertTrue(redirectCaptor.getValue().contains("error=not_allowed"));
    }

    private OAuth2LoginSuccessHandler handler() {
        return new OAuth2LoginSuccessHandler(
                authUserUseCase,
                createUserUseCase,
                authCookieService,
                SUCCESS_URL,
                FAILURE_URL
        );
    }

    private OAuth2AuthenticationToken oauthToken(OAuthUserDto dto) {
        OAuth2User delegate = mock(OAuth2User.class);
        doReturn(AuthorityUtils.NO_AUTHORITIES).when(delegate).getAuthorities();
        GoogleOAuthPrincipal principal = new GoogleOAuthPrincipal(delegate, dto);
        return new OAuth2AuthenticationToken(principal, principal.getAuthorities(), "google");
    }
}

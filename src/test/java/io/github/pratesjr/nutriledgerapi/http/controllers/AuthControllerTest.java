package io.github.pratesjr.nutriledgerapi.http.controllers;

import io.github.pratesjr.nutriledgerapi.application.dtos.OAuthUserDto;
import io.github.pratesjr.nutriledgerapi.application.ports.AuthCookieServicePort;
import io.github.pratesjr.nutriledgerapi.application.ports.AuthUserUseCasePort;
import io.github.pratesjr.nutriledgerapi.http.dtos.AuthResultDto;
import io.github.pratesjr.nutriledgerapi.infra.security.GoogleOAuthPrincipal;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private final AuthUserUseCasePort authUserUseCase = mock(AuthUserUseCasePort.class);
    private final AuthCookieServicePort authCookieService = mock(AuthCookieServicePort.class);
    private final AuthController controller = new AuthController(authUserUseCase, authCookieService);

    @Test
    void shouldRemoveCookieOnSignOut() {
        HttpServletResponse response = mock(HttpServletResponse.class);

        ResponseEntity<Void> result = controller.signOut(response);

        verify(authCookieService).removeAuthCookie(response);
        assertEquals(204, result.getStatusCode().value());
    }

    @Test
    void shouldSetCookieOnSignIn() {
        OAuthUserDto dto = new OAuthUserDto("u@test.com", "User", null);
        DefaultOAuth2User delegate = new DefaultOAuth2User(AuthorityUtils.NO_AUTHORITIES, Map.of("sub", "1"), "sub");
        GoogleOAuthPrincipal principal = new GoogleOAuthPrincipal(delegate, dto);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(authUserUseCase.authenticate(dto)).thenReturn(new AuthResultDto("jwt-token"));

        ResponseEntity<Void> result = controller.signIn(principal, response);

        verify(authCookieService).addAuthCookie(response, "jwt-token");
        assertEquals(204, result.getStatusCode().value());
    }

    @Test
    void shouldRedirectToGoogleOnSignInGet() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        controller.startGoogleSignIn(request, response);

        assertEquals("/oauth2/authorization/google", response.getRedirectedUrl());
    }
}

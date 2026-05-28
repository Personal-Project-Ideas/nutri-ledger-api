package io.github.pratesjr.nutriledgerapi.infra.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AuthCookieServiceTest {

    @Test
    void shouldAddAuthCookieWithExpectedAttributes() {
        AuthCookieService service = new AuthCookieService(true);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String token = "jwt-token-example";

        service.addAuthCookie(response, token);

        String setCookie = captureSetCookieHeader(response);
        verify(response, never()).addCookie(any(Cookie.class));

        assertTrue(setCookie.contains(AuthCookieService.AUTH_COOKIE_NAME + "=" + token));
        assertTrue(setCookie.contains("Max-Age=" + (60 * 60 * 24)));
        assertTrue(setCookie.contains("Path=/"));
        assertTrue(setCookie.contains("HttpOnly"));
        assertTrue(setCookie.contains("Secure"));
        assertTrue(setCookie.contains("SameSite=Strict"));
    }

    @Test
    void shouldWriteSingleCanonicalSetCookieHeader() {
        AuthCookieService service = new AuthCookieService(true);
        HttpServletResponse response = mock(HttpServletResponse.class);

        service.addAuthCookie(response, "token");

        verify(response, times(1)).setHeader(eq("Set-Cookie"), any(String.class));
        verify(response, never()).addCookie(any(Cookie.class));
    }

    @Test
    void shouldOmitSecureAttributeWhenCookieSecureIsFalse() {
        AuthCookieService service = new AuthCookieService(false);
        HttpServletResponse response = mock(HttpServletResponse.class);

        service.addAuthCookie(response, "token");

        String setCookie = captureSetCookieHeader(response);
        assertFalse(setCookie.contains("Secure"));
    }

    @Test
    void shouldClearAuthCookieOnLogoutWithMaxAgeZero() {
        AuthCookieService service = new AuthCookieService(true);
        HttpServletResponse response = mock(HttpServletResponse.class);

        service.removeAuthCookie(response);

        String setCookie = captureSetCookieHeader(response);
        verify(response, never()).addCookie(any(Cookie.class));

        assertTrue(setCookie.startsWith(AuthCookieService.AUTH_COOKIE_NAME + "="));
        assertTrue(setCookie.contains("Max-Age=0"));
        assertTrue(setCookie.contains("Path=/"));
        assertTrue(setCookie.contains("HttpOnly"));
        assertTrue(setCookie.contains("Secure"));
        assertTrue(setCookie.contains("SameSite=Strict"));
    }

    private static String captureSetCookieHeader(HttpServletResponse response) {
        ArgumentCaptor<String> headerValueCaptor = ArgumentCaptor.forClass(String.class);
        verify(response).setHeader(eq("Set-Cookie"), headerValueCaptor.capture());
        return headerValueCaptor.getValue();
    }
}

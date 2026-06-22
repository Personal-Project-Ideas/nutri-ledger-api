package io.github.pratesjr.nutriledgerapi.infra.security;

import io.github.pratesjr.nutriledgerapi.infra.services.AuthCookieService;
import io.github.pratesjr.nutriledgerapi.utils.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.SecretKey;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class JwtCookieAuthenticationFilterTest {

    private static final String SECRET = "test-secret-key-with-at-least-32-bytes!!";

    private JwtUtil jwtUtil;
    private JwtCookieAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(SECRET, 3600);
        filter = new JwtCookieAuthenticationFilter(jwtUtil);
    }

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldSetAuthenticationWhenCookieIsValid() throws Exception {
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        String token = signedToken(userId, "user@example.com", System.currentTimeMillis() / 1000L + 3600L);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/portions");
        request.setCookies(new Cookie(AuthCookieService.AUTH_COOKIE_NAME, token));
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertInstanceOf(UsernamePasswordAuthenticationToken.class, authentication);
        JwtUserPrincipal principal = (JwtUserPrincipal) authentication.getPrincipal();
        assertEquals(userId, principal.userId());
        assertEquals("user@example.com", principal.email());
        verify(chain).doFilter(request, response);
    }

    @Test
    void shouldLeaveUnauthenticatedWhenCookieIsInvalid() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/portions");
        request.setCookies(new Cookie(AuthCookieService.AUTH_COOKIE_NAME, "not-a-valid-jwt"));
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
    }

    @Test
    void shouldSkipFilterForPublicPaths() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/health/status");
        assertTrue(filter.shouldNotFilter(request));
    }

    private static String signedToken(UUID userId, String email, long exp) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
        long now = System.currentTimeMillis() / 1000L;
        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .claim("iat", now)
                .claim("exp", exp)
                .signWith(secretKey)
                .compact();
    }
}

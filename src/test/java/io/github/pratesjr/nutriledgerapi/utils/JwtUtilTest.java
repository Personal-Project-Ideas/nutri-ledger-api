package io.github.pratesjr.nutriledgerapi.utils;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import io.github.pratesjr.nutriledgerapi.domain.errors.AuthTokenGenerationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

class JwtUtilTest {

    @Test
    void shouldParseAndValidateTokenClaims() {
        String secret = "test-secret-key-with-at-least-32-bytes!!";
        JwtUtil jwtUtil = new JwtUtil(secret, 3600);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        long now = System.currentTimeMillis() / 1000L;
        long exp = now + 3600L;

        String token = Jwts.builder()
                .subject("123")
                .claim("email", "user@example.com")
                .claim("iat", now)
                .claim("exp", exp)
                .signWith(secretKey)
                .compact();

        JwtUtil.JwtClaims claims = jwtUtil.parseAndValidate(token);

        assertEquals(123L, claims.getUserId());
        assertEquals("user@example.com", claims.getEmail());
        assertTrue(claims.getExp() > now);
    }
    
    @Test
    void shouldThrowExceptionForExpiredToken() {
        String secret = "test-secret-key-with-at-least-32-bytes!!";
        JwtUtil jwtUtil = new JwtUtil(secret, 3600);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        long now = System.currentTimeMillis() / 1000L;
        long exp = now - 3600L;
        
        String token = Jwts.builder()
                .subject("123")
                .claim("email", "user@example.com")
                .claim("iat", now)
                .claim("exp", exp)
                .signWith(secretKey)
                .compact();
                
        AuthTokenGenerationException exception = assertThrows(
                AuthTokenGenerationException.class,
                () -> jwtUtil.parseAndValidate(token)
        );
        assertTrue(exception.getMessage().contains("Invalid JWT:"));
    }
}

package io.github.pratesjr.nutriledgerapi.utils;

import io.github.pratesjr.nutriledgerapi.domain.errors.AuthTokenGenerationException;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final SecretKey secretKey;
    private final long expiration;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration * 1000; // seconds to ms
    }
    public String generateToken(User user) {
        try {
            long now = System.currentTimeMillis() / 1000L; // epoch seconds
            long exp = now + (expiration / 1000L);
            return Jwts.builder()
                    .claim("sub", user.getId()) // or user.getUserId() if that's the method name
                    .claim("email", user.getEmail())
                    .claim("name", user.getFullName())
                    .claim("iat", now)
                    .claim("exp", exp)
                    .signWith(secretKey)
                    .compact();
        } catch (Exception e) {
            throw new AuthTokenGenerationException("Failed to generate JWT: " + e.getMessage());
        }
    }
}

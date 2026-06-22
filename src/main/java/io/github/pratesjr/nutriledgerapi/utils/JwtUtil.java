package io.github.pratesjr.nutriledgerapi.utils;

import io.github.pratesjr.nutriledgerapi.domain.errors.AuthTokenGenerationException;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Objects;
import java.util.UUID;

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
                    .subject(user.getId().toString())
                    .claim("email", user.getEmail())
                    .claim("name", user.getFullName())
                    .claim("iat", now)
                    .claim("exp", exp)
                    .signWith(secretKey)
                    .compact();
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthTokenGenerationException("Failed to generate JWT: " + e.getMessage());
        }
    }

    private JwtClaims validateJwtClaims(Claims claims) {
        UUID userId = parseSubject(claims);
        String email = claims.get("email", String.class);
        Long exp = getLongClaim(claims, "exp");
        long now = System.currentTimeMillis() / 1000L; // epoch seconds

        if (userId == null) {
            throw new AuthTokenGenerationException("Invalid JWT: missing or invalid subject");
        }
        if (email == null || email.isBlank()) {
            throw new AuthTokenGenerationException("Invalid JWT: missing email claim");
        }
        if (exp == null) {
            throw new AuthTokenGenerationException("Invalid JWT: missing expiration claim");
        }
        if (exp <= now) {
            throw new AuthTokenGenerationException("Invalid JWT: token has expired");
        }
        return new JwtClaims(userId, email, exp);
    }

    private UUID parseSubject(Claims claims) {
        String subject = claims.getSubject();
        if (subject == null || subject.isBlank()) {
            return null;
        }
        try {
            return UUID.fromString(subject.trim());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public JwtClaims parseAndValidate(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return validateJwtClaims(claims);
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthTokenGenerationException("Invalid JWT: " + e.getMessage());
        }
    }

    private Long getLongClaim(Claims claims, String claimName) {
        Object value = claims.get(claimName);
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        if (value instanceof String && !((String) value).isBlank()) {
            try {
                return Long.valueOf((String) value);
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }

    public static final class JwtClaims {
        private final UUID userId;
        private final String email;
        private final long exp;

        public JwtClaims(UUID userId, String email, long exp) {
            this.userId = Objects.requireNonNull(userId, "userId");
            this.email = Objects.requireNonNull(email, "email");
            this.exp = exp;
        }

        public UUID getUserId() {
            return userId;
        }

        public String getEmail() {
            return email;
        }

        public long getExp() {
            return exp;
        }
    }
}

package io.github.pratesjr.nutriledgerapi.infra.security;

import java.util.Objects;

import org.springframework.util.AntPathMatcher;

/**
 * Single allowlist for anonymous access: OAuth handshake, app auth routes, health, OpenAPI/Swagger.
 * Business APIs (e.g. portions) require authentication — see {@link #AUTHENTICATED_PATTERNS}.
 */
public final class PublicEndpointPaths {

    private PublicEndpointPaths() {}

    /** Ant-style patterns that always require a valid session/JWT ({@code authenticated()}). */
    public static final String[] AUTHENTICATED_PATTERNS = {
            "/portions",
            "/portions/**",
    };

    /** Ant-style patterns for {@code authorizeHttpRequests(...).requestMatchers(...).permitAll()}. */
    public static final String[] PERMIT_ALL_PATTERNS = {
            "/oauth2/**",
            "/login/oauth2/**",
            "/auth/google/signin",
            "/auth/signout",
            "/health/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    /**
     * Path without servlet context path (aligned with {@link JwtCookieAuthenticationFilter}).
     */
    public static boolean isPublicPath(String pathWithinApplication) {
        final String path = (pathWithinApplication == null || pathWithinApplication.isEmpty())
                ? "/"
                : pathWithinApplication;
        for (String pattern : PERMIT_ALL_PATTERNS) {
            if (MATCHER.match(Objects.requireNonNull(pattern), path)) {
                return true;
            }
        }
        return false;
    }
}

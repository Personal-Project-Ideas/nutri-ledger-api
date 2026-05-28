package io.github.pratesjr.nutriledgerapi.infra.security;

import java.io.Serializable;
import java.util.UUID;

/**
 * Principal established after validating the JWT from the {@code AUTH_TOKEN} cookie.
 */
public record JwtUserPrincipal(UUID userId, String email) implements Serializable {}

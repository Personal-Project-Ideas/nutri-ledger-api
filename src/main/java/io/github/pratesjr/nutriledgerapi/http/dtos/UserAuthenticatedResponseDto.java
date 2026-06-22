package io.github.pratesjr.nutriledgerapi.http.dtos;

import java.util.UUID;

public record UserAuthenticatedResponseDto(UUID userId, String accessToken, String refreshToken) {}

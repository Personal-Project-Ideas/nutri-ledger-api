package io.github.pratesjr.nutriledgerapi.http.dtos;

import java.util.UUID;

public class UserAuthenticatedResponseDto {
    private final UUID userId;
    private final String accessToken;
    private final String refreshToken;

    public UserAuthenticatedResponseDto(UUID userId, String accessToken, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}


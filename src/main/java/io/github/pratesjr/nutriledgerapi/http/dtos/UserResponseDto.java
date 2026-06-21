package io.github.pratesjr.nutriledgerapi.http.dtos;

import java.util.UUID;

public record UserResponseDto(UUID id, String fullName, Integer age, String createdAt, String updatedAt) {}

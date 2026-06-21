package io.github.pratesjr.nutriledgerapi.http.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AllowedUserDto(
        @Email(message = "Invalid email format") @NotBlank(message = "Email cannot be blank") String email
) {}

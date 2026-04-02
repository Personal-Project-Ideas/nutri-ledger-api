package io.github.pratesjr.nutriledgerapi.http.dtos;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AllowedUserDto {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    @Nonnull
    private String email = "";
}

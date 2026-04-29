package io.github.pratesjr.nutriledgerapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuthUserDto {
    private String email;
    private String fullName;
    private String birthdate;
}

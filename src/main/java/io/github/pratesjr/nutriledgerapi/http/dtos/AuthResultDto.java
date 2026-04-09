package io.github.pratesjr.nutriledgerapi.http.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResultDto {
    private String token;

    public AuthResultDto(String token) {
        this.token = token;
    }
}

package io.github.pratesjr.nutriledgerapi.http.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserResponseDto {
    UUID id;
    String fullName;
    Integer age;
    String createdAt;
    String updatedAt;

}

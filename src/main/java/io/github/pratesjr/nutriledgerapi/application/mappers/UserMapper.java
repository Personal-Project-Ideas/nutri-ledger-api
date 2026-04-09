package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.http.dtos.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // UserResponseDto -> User
    default User toModel(UserResponseDto dto) {
        if (dto == null) return null;
        return new User(
            dto.getId(),
            dto.getFullName(),
            dto.getAge(),
            dto.getCreatedAt() != null ? java.time.Instant.parse(dto.getCreatedAt()) : null,
            dto.getUpdatedAt() != null ? java.time.Instant.parse(dto.getUpdatedAt()) : null
        );
    }
}

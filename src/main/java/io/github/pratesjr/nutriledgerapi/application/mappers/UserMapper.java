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
            dto.id(),
            dto.fullName(),
            null,
            dto.age(),
            dto.createdAt() != null ? java.time.Instant.parse(dto.createdAt()) : null,
            dto.updatedAt() != null ? java.time.Instant.parse(dto.updatedAt()) : null
        );
    }
}

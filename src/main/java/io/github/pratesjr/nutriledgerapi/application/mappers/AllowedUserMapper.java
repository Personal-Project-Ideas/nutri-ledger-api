package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.http.dtos.AllowedUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AllowedUserMapper {

    // AllowedUserDto -> AllowedUser (uses constructor for immutable model)
    default AllowedUser toModel(AllowedUserDto dto) {
        if (dto == null) return null;
        return new AllowedUser(dto.getEmail());
    }

    // AllowedUser -> AllowedUserDto
    @Mapping(source = "email", target = "email")
    AllowedUserDto toDto(AllowedUser model);
}

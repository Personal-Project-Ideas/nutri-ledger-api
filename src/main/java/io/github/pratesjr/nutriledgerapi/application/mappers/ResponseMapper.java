package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.Portion;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionResponseDto;
import io.github.pratesjr.nutriledgerapi.http.dtos.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface ResponseMapper {
    // Portion -> PortionResponseDto
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "servingQuantity", target = "servingQuantity")
    @Mapping(source = "servingUnit", target = "servingUnit")
    @Mapping(source = "caloriesPerServing", target = "caloriesPerServing")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "instantToString")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "instantToString")
    PortionResponseDto toPortionResponseDto(Portion model);

    // User -> UserResponseDto
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "instantToString")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "instantToString")
    UserResponseDto toUserResponseDto(User model);

    @Named("instantToString")
    default String instantToString(Instant instant) {
        return instant != null ? instant.toString() : null;
    }
}


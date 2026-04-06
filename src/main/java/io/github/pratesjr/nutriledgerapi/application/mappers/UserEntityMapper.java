package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.infra.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity toEntity(User model);
    User toModel(UserEntity entity);
}


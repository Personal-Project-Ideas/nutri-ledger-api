package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.infra.entities.AllowedUserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AllowedUserEntityMapper {
    AllowedUserEntity toEntity(AllowedUser model);
    AllowedUser toModel(AllowedUserEntity entity);
}


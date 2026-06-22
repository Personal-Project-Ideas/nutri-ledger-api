package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.infra.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    default UserEntity toEntity(User model) {
        if (model == null) {
            return null;
        }
        return new UserEntity(
            model.getId(),
            model.getFullName(),
            model.getAge(),
            model.getEmail(),
            model.getCreatedAt(),
            model.getUpdatedAt()
        );
    }

    default User toModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(
            entity.getId(),
            entity.getFullName(),
            entity.getEmail(),
            entity.getAge(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}


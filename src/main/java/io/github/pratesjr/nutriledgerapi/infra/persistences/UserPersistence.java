package io.github.pratesjr.nutriledgerapi.infra.persistences;

import io.github.pratesjr.nutriledgerapi.application.mappers.UserEntityMapper;
import io.github.pratesjr.nutriledgerapi.application.ports.UserPersistencePort;
import io.github.pratesjr.nutriledgerapi.domain.errors.UserNotFoundException;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.infra.entities.UserEntity;
import io.github.pratesjr.nutriledgerapi.infra.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserPersistence  implements UserPersistencePort {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    UserPersistence(UserRepository userRepository, UserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
    }
    
    @Override
    public User create(User user) {
        UserEntity entity = userEntityMapper.toEntity(user);
        entity.setId(null);
        UserEntity saved = userRepository.save(entity);

        return userEntityMapper.toModel(saved);
    }
    
    @Override
    public User findById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Optional<UserEntity> userOpt = userRepository.findById(uuid);

            if (userOpt.isEmpty()) {
                throw new UserNotFoundException();
            }
            return userOpt.map(userEntityMapper::toModel).get();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }
}

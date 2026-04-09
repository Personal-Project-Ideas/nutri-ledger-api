package io.github.pratesjr.nutriledgerapi.infra.persistences;

import io.github.pratesjr.nutriledgerapi.application.ports.AllowedUserPersistencePort;
import io.github.pratesjr.nutriledgerapi.application.mappers.AllowedUserEntityMapper;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.infra.entities.AllowedUserEntity;
import io.github.pratesjr.nutriledgerapi.infra.repositories.AllowedUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AllowedUserPersistence implements AllowedUserPersistencePort {
    private final AllowedUserRepository repository;
    private final AllowedUserEntityMapper mapper;

    public AllowedUserPersistence(AllowedUserRepository repository, AllowedUserEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AllowedUser findByEmail(String email) {
        AllowedUserEntity entity = repository.findByEmail(email).orElse(null);
        return entity != null ? mapper.toModel(entity) : null;
    }
}

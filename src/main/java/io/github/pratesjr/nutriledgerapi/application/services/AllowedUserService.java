package io.github.pratesjr.nutriledgerapi.application.services;

import io.github.pratesjr.nutriledgerapi.application.ports.AllowedUserPersistencePort;
import io.github.pratesjr.nutriledgerapi.application.ports.AllowedUserPort;
import io.github.pratesjr.nutriledgerapi.domain.errors.UserNotAllowedToBeCreatedException;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllowedUserService implements AllowedUserPort {
    private final AllowedUserPersistencePort persistence;

    @Autowired
    public AllowedUserService(AllowedUserPersistencePort persistence) {
        this.persistence = persistence;
    }

    @Override
    public boolean isAllowed(String email) {
        return this.persistence.findByEmail(email) != null;
    }
}

package io.github.pratesjr.nutriledgerapi.application.ports;

import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;

public interface AllowedUserPersistencePort {
    AllowedUser findByEmail(String email);
}


package io.github.pratesjr.nutriledgerapi.application.ports;

import io.github.pratesjr.nutriledgerapi.domain.models.User;

public interface UserPersistencePort {
    User create(User user);
    User findById(String id);

}

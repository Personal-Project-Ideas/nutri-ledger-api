package io.github.pratesjr.nutriledgerapi.application.ports;

import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.domain.models.User;

public interface CreateUserUseCase {
    User createUser(AllowedUser user);
}

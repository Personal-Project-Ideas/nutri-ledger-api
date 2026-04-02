package io.github.pratesjr.nutriledgerapi.application.usecases;

import io.github.pratesjr.nutriledgerapi.application.ports.CreateUserUseCase;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import org.springframework.stereotype.Service;

@Service
public class CreateUser  implements CreateUserUseCase {
    @Override
    public User process(AllowedUser user) {
        return null;
    }
}

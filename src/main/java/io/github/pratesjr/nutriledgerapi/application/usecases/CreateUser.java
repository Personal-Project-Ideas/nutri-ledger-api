package io.github.pratesjr.nutriledgerapi.application.usecases;

import io.github.pratesjr.nutriledgerapi.application.ports.AllowedUserPort;
import io.github.pratesjr.nutriledgerapi.application.ports.CreateUserUseCase;
import io.github.pratesjr.nutriledgerapi.domain.errors.UserNotAllowedToBeCreatedException;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import org.springframework.stereotype.Service;

@Service
public class CreateUser  implements CreateUserUseCase {
    private final AllowedUserPort allowedUserService;

    public CreateUser(AllowedUserPort allowedUserService) {
        this.allowedUserService = allowedUserService;
    }

    @Override
    public User process(AllowedUser user) {
        if(!this.allowedUserService.isAllowed(user.getEmail())){
            throw new UserNotAllowedToBeCreatedException(user.getEmail());

        }

        return null;
    }
}

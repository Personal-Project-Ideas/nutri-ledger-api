package io.github.pratesjr.nutriledgerapi.application.ports;

import io.github.pratesjr.nutriledgerapi.application.dtos.OAuthUserDto;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.domain.models.User;

public interface CreateUserUseCasePort {
    User process(AllowedUser userEmail, OAuthUserDto oAuthInfo);
}

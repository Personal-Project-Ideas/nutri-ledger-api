package io.github.pratesjr.nutriledgerapi.application.ports;

import io.github.pratesjr.nutriledgerapi.application.dtos.OAuthUserDto;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.http.dtos.AuthResultDto;

public interface AuthUserUseCasePort {
    AuthResultDto authenticate(OAuthUserDto oAuthUser);
}

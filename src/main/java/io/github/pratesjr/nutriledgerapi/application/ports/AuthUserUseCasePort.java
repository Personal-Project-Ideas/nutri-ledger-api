package io.github.pratesjr.nutriledgerapi.application.ports;

import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.http.dtos.AuthResultDto;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface AuthUserUseCasePort {
    AuthResultDto authenticate(OAuth2User oAuth2User);
}

package io.github.pratesjr.nutriledgerapi.application.services;

import io.github.pratesjr.nutriledgerapi.application.ports.AuthUserUseCasePort;
import io.github.pratesjr.nutriledgerapi.application.ports.UserPersistencePort;
import io.github.pratesjr.nutriledgerapi.domain.errors.UserNotFoundException;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.http.dtos.AuthResultDto;

import io.github.pratesjr.nutriledgerapi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AuthUserUseCase implements AuthUserUseCasePort {
    private final JwtUtil jwtUtil;
    private final UserPersistencePort userPersistence;

    @Autowired
    public AuthUserUseCase(JwtUtil jwtUtil, UserPersistencePort userPersistence) {
        this.jwtUtil = jwtUtil;
        this.userPersistence = userPersistence;
    }

    @Override
    public AuthResultDto authenticate(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        User user = this.userPersistence.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        String token = jwtUtil.generateToken(user);
        return new AuthResultDto(token);
    }
}

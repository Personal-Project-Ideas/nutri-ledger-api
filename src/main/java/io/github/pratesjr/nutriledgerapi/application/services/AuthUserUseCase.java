package io.github.pratesjr.nutriledgerapi.application.services;

import io.github.pratesjr.nutriledgerapi.application.ports.AuthUserUseCasePort;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.http.dtos.AuthResultDto;

import io.github.pratesjr.nutriledgerapi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AuthUserUseCase implements AuthUserUseCasePort {
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthUserUseCase(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResultDto authenticate(OAuth2User oAuth2User) {
        String token = jwtUtil.generateToken(oAuth2User);
        return new AuthResultDto(token);
    }
}

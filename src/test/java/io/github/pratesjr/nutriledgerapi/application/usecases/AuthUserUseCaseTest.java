package io.github.pratesjr.nutriledgerapi.application.usecases;

import io.github.pratesjr.nutriledgerapi.application.dtos.OAuthUserDto;
import io.github.pratesjr.nutriledgerapi.application.ports.UserPersistencePort;
import io.github.pratesjr.nutriledgerapi.domain.errors.UserNotFoundException;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.http.dtos.AuthResultDto;
import io.github.pratesjr.nutriledgerapi.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUserUseCaseTest {

    private final JwtUtil jwtUtil = new JwtUtil("test-secret-key-for-unit-tests-padded-xx", 3600L);
    @Mock private UserPersistencePort userPersistence;

    private AuthUserUseCase useCase() {
        return new AuthUserUseCase(jwtUtil, userPersistence);
    }

    @Test
    void shouldReturnTokenWhenUserFound() {
        User user = new User(UUID.randomUUID(), "User", "user@test.com", null, null, null);
        when(userPersistence.findByEmail("user@test.com")).thenReturn(user);

        AuthResultDto result = useCase().authenticate(new OAuthUserDto("user@test.com", "User", null));

        assertNotNull(result.token());
        assertFalse(result.token().isBlank());
    }

    @Test
    void shouldThrowUserNotFoundWhenUserMissing() {
        when(userPersistence.findByEmail("missing@test.com")).thenReturn(null);
        OAuthUserDto dto = new OAuthUserDto("missing@test.com", "User", null);
        AuthUserUseCase useCase = useCase();

        assertThrows(UserNotFoundException.class, () -> useCase.authenticate(dto));
    }
}

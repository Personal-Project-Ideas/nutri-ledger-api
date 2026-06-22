package io.github.pratesjr.nutriledgerapi.application.usecases;

import io.github.pratesjr.nutriledgerapi.application.dtos.OAuthUserDto;
import io.github.pratesjr.nutriledgerapi.application.mappers.OAuthUserMapper;
import io.github.pratesjr.nutriledgerapi.application.ports.AllowedUserPort;
import io.github.pratesjr.nutriledgerapi.application.ports.UserPersistencePort;
import io.github.pratesjr.nutriledgerapi.domain.errors.UserNotAllowedToBeCreatedException;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateUserUseCaseTest {

    private final AllowedUserPort allowedUserPort = mock(AllowedUserPort.class);
    private final UserPersistencePort userPersistence = mock(UserPersistencePort.class);
    private final CreateUserUseCase useCase = new CreateUserUseCase(allowedUserPort, userPersistence, new OAuthUserMapper());

    @Test
    void shouldThrowWhenUserNotAllowed() {
        when(allowedUserPort.isAllowed("blocked@test.com")).thenReturn(false);
        AllowedUser allowedUser = new AllowedUser("blocked@test.com");
        OAuthUserDto dto = new OAuthUserDto("blocked@test.com", "Blocked", null);

        assertThrows(UserNotAllowedToBeCreatedException.class, () -> useCase.process(allowedUser, dto));
    }

    @Test
    void shouldReturnExistingUserWhenAlreadyExists() {
        User existing = new User("User", "user@test.com", null);
        when(allowedUserPort.isAllowed("user@test.com")).thenReturn(true);
        when(userPersistence.findByEmail("user@test.com")).thenReturn(existing);

        User result = useCase.process(new AllowedUser("user@test.com"), new OAuthUserDto("user@test.com", "User", null));

        assertEquals(existing, result);
        verify(userPersistence, never()).create(any());
    }

    @Test
    void shouldCreateNewUserWhenAllowed() {
        User created = new User("New User", "new@test.com", null);
        when(allowedUserPort.isAllowed("new@test.com")).thenReturn(true);
        when(userPersistence.findByEmail("new@test.com")).thenReturn(null);
        when(userPersistence.create(any())).thenReturn(created);

        User result = useCase.process(new AllowedUser("new@test.com"), new OAuthUserDto("new@test.com", "New User", null));

        verify(userPersistence).create(any());
        assertEquals(created, result);
    }

    @Test
    void shouldHandleNullBirthdate() {
        when(allowedUserPort.isAllowed("u@test.com")).thenReturn(true);
        when(userPersistence.findByEmail("u@test.com")).thenReturn(null);
        when(userPersistence.create(any())).thenAnswer(inv -> inv.getArgument(0));

        User result = useCase.process(new AllowedUser("u@test.com"), new OAuthUserDto("u@test.com", "User", null));

        assertNull(result.getAge());
    }

    @Test
    void shouldHandleInvalidBirthdateFormat() {
        when(allowedUserPort.isAllowed("u@test.com")).thenReturn(true);
        when(userPersistence.findByEmail("u@test.com")).thenReturn(null);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        when(userPersistence.create(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        useCase.process(new AllowedUser("u@test.com"), new OAuthUserDto("u@test.com", "User", "not-a-date"));

        assertNull(captor.getValue().getAge());
    }
}

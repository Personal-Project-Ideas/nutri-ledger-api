package io.github.pratesjr.nutriledgerapi.domain.errors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserConflictExceptionTest {
    @Test
    void defaultConstructorShouldUseErrorCodesMessage() {
        UserConflictException ex = new UserConflictException();
        assertEquals(ErrorCodes.CODES.get(UserConflictException.CODE).description, ex.getMessage());
        assertInstanceOf(RuntimeException.class, ex);
    }

    @Test
    void customMessageShouldBeUsedIfProvided() {
        String custom = "Usuário já existe";
        UserConflictException ex = new UserConflictException(custom);
        assertEquals(custom, ex.getMessage());
    }
}


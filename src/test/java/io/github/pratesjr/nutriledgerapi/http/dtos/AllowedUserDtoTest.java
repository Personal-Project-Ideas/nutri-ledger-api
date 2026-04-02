package io.github.pratesjr.nutriledgerapi.http.dtos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AllowedUserDtoTest {
    @Test
    void testAllArgsConstructorAndGetters() {
        AllowedUserDto dto = new AllowedUserDto("user@email.com");
        assertEquals("user@email.com", dto.getEmail());
    }

    @Test
    void testSetters() {
        AllowedUserDto dto = new AllowedUserDto();
        dto.setEmail("another@email.com");
        assertEquals("another@email.com", dto.getEmail());
    }
}


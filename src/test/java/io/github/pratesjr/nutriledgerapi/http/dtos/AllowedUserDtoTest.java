package io.github.pratesjr.nutriledgerapi.http.dtos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AllowedUserDtoTest {
    @Test
    void testGettersAndSetters() {
        AllowedUserDto dto = new AllowedUserDto();
        dto.setEmail("user@email.com");
        assertEquals("user@email.com", dto.getEmail());
    }

    @Test
    void testSetters() {
        AllowedUserDto dto = new AllowedUserDto();
        dto.setEmail("another@email.com");
        assertEquals("another@email.com", dto.getEmail());
    }
}


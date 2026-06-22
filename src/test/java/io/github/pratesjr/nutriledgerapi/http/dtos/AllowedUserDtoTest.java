package io.github.pratesjr.nutriledgerapi.http.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AllowedUserDtoTest {

    @Test
    void testAccessor() {
        AllowedUserDto dto = new AllowedUserDto("user@email.com");
        assertEquals("user@email.com", dto.email());
    }

    @Test
    void testEquality() {
        AllowedUserDto first = new AllowedUserDto("user@email.com");
        AllowedUserDto second = new AllowedUserDto("user@email.com");
        assertEquals(first, second);
    }
}

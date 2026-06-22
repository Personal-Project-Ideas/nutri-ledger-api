package io.github.pratesjr.nutriledgerapi.http.dtos;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseDtoTest {

    @Test
    void testConstructorAndAccessors() {
        UUID id = UUID.randomUUID();
        UserResponseDto dto = new UserResponseDto(id, "Test User", 30,
                "2024-06-01T12:00:00Z", "2024-06-01T12:10:00Z");

        assertAll(
                () -> assertEquals(id, dto.id()),
                () -> assertEquals("Test User", dto.fullName()),
                () -> assertEquals(30, dto.age()),
                () -> assertEquals("2024-06-01T12:00:00Z", dto.createdAt()),
                () -> assertEquals("2024-06-01T12:10:00Z", dto.updatedAt())
        );
    }
}

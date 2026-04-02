package io.github.pratesjr.nutriledgerapi.http.dtos;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseDtoTest {
    @Test
    void testAllArgsConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        UserResponseDto dto = new UserResponseDto();
        dto.setId(id);
        dto.setFullName("Test User");
        dto.setAge(30);
        dto.setCreatedAt("2024-06-01T12:00:00Z");
        dto.setUpdatedAt("2024-06-01T12:10:00Z");
        assertEquals(id, dto.getId());
        assertEquals("Test User", dto.getFullName());
        assertEquals(30, dto.getAge());
        assertEquals("2024-06-01T12:00:00Z", dto.getCreatedAt());
        assertEquals("2024-06-01T12:10:00Z", dto.getUpdatedAt());
    }

    @Test
    void testSetters() {
        UserResponseDto dto = new UserResponseDto();
        dto.setFullName("Another User");
        assertEquals("Another User", dto.getFullName());
    }
}


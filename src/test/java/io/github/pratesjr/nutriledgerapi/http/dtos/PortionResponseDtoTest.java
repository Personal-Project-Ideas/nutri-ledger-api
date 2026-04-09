package io.github.pratesjr.nutriledgerapi.http.dtos;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PortionResponseDtoTest {
    @Test
    void testAllArgsConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        PortionResponseDto dto = new PortionResponseDto();
        dto.setId(id);
        dto.setName("Oatmeal");
        dto.setServingQuantity(120);
        dto.setServingUnit("g");
        dto.setCaloriesPerServing(150);
        dto.setCreatedAt("2024-06-01T12:00:00Z");
        dto.setUpdatedAt("2024-06-01T12:10:00Z");
        assertEquals(id, dto.getId());
        assertEquals("Oatmeal", dto.getName());
        assertEquals(120, dto.getServingQuantity());
        assertEquals("g", dto.getServingUnit());
        assertEquals(150, dto.getCaloriesPerServing());
        assertEquals("2024-06-01T12:00:00Z", dto.getCreatedAt());
        assertEquals("2024-06-01T12:10:00Z", dto.getUpdatedAt());
    }

    @Test
    void testSetters() {
        PortionResponseDto dto = new PortionResponseDto();
        dto.setName("Another Portion");
        assertEquals("Another Portion", dto.getName());
    }
}


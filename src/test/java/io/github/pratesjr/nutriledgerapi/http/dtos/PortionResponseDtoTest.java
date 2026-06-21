package io.github.pratesjr.nutriledgerapi.http.dtos;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PortionResponseDtoTest {

    @Test
    void testConstructorAndAccessors() {
        UUID id = UUID.randomUUID();
        PortionResponseDto dto = new PortionResponseDto(id, "Oatmeal", 120, "g", 150,
                "2024-06-01T12:00:00Z", "2024-06-01T12:10:00Z");

        assertAll(
                () -> assertEquals(id, dto.id()),
                () -> assertEquals("Oatmeal", dto.name()),
                () -> assertEquals(120, dto.servingQuantity()),
                () -> assertEquals("g", dto.servingUnit()),
                () -> assertEquals(150, dto.caloriesPerServing()),
                () -> assertEquals("2024-06-01T12:00:00Z", dto.createdAt()),
                () -> assertEquals("2024-06-01T12:10:00Z", dto.updatedAt())
        );
    }
}

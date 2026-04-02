package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.Portion;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PortionResponseDtoMapperTest {

    private final PortionResponseDtoMapper mapper = new PortionResponseDtoMapper();

    @Nested
    @DisplayName("toDto")
    class ToDto {

        @Test
        @DisplayName("should return null when model is null")
        void shouldReturnNullWhenModelIsNull() {
            PortionResponseDto dto = mapper.toDto(null);

            assertNull(dto);
        }

        @Test
        @DisplayName("should map all fields from model to response dto")
        void shouldMapAllFieldsFromModelToResponseDto() {
            UUID id = UUID.randomUUID();
            Instant createdAt = Instant.now();
            Instant updatedAt = createdAt.plusSeconds(60);

            Portion model = new Portion(
                    id,
                    "Oatmeal",
                    120,
                    "g",
                    150,
                    createdAt,
                    updatedAt
            );

            PortionResponseDto dto = mapper.toDto(model);

            assertAll(
                    () -> assertEquals(id, dto.getId()),
                    () -> assertEquals("Oatmeal", dto.getName()),
                    () -> assertEquals(120, dto.getServingQuantity()),
                    () -> assertEquals("g", dto.getServingUnit()),
                    () -> assertEquals(150, dto.getCaloriesPerServing()),
                    () -> assertEquals(createdAt.toString(), dto.getCreatedAt()),
                    () -> assertEquals(updatedAt.toString(), dto.getUpdatedAt())
            );
        }
    }
}

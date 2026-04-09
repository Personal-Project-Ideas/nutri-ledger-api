package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.Portion;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionDto;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PortionMapperTest {

    private PortionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PortionMapper.class);
    }

    @Nested
    @DisplayName("toModel (from PortionDto)")
    class ToModel {

        @Test
        @DisplayName("should return null when dto is null")
        void shouldReturnNullWhenDtoIsNull() {
            Portion mapped = mapper.toModel(null);

            assertNull(mapped);
        }

        @Test
        @DisplayName("should map all fields from dto to model")
        void shouldMapAllFieldsFromDtoToModel() {
            PortionDto dto = new PortionDto();
            dto.setName("Arroz Integral");
            dto.setServingQuantity(100);
            dto.setServingUnit("g");
            dto.setCaloriesPerServing(130);

            Portion mapped = mapper.toModel(dto);

            assertAll(
                    () -> assertEquals("Arroz Integral", mapped.getName()),
                    () -> assertEquals("arroz integral", mapped.getNormalizedName()),
                    () -> assertEquals(100, mapped.getServingQuantity()),
                    () -> assertEquals("g", mapped.getServingUnit()),
                    () -> assertEquals(130, mapped.getCaloriesPerServing())
            );
        }
    }

    @Nested
    @DisplayName("toDto (to PortionDto)")
    class ToDto {

        @Test
        @DisplayName("should return null when model is null")
        void shouldReturnNullWhenModelIsNull() {
            PortionDto dto = mapper.toDto(null);

            assertNull(dto);
        }

        @Test
        @DisplayName("should map all fields from model to dto")
        void shouldMapAllFieldsFromModelToDto() {
            Portion model = new Portion(
                    "Oatmeal",
                    120,
                    "g",
                    150
            );

            PortionDto dto = mapper.toDto(model);

            assertAll(
                    () -> assertEquals("Oatmeal", dto.getName()),
                    () -> assertEquals(120, dto.getServingQuantity()),
                    () -> assertEquals("g", dto.getServingUnit()),
                    () -> assertEquals(150, dto.getCaloriesPerServing())
            );
        }
    }
}


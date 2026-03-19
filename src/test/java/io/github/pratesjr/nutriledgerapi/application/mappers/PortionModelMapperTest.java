package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.Portion;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PortionModelMapperTest {

    private final PortionModelMapper mapper = new PortionModelMapper();

    @Nested
    @DisplayName("toModel")
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
    @DisplayName("toModelList")
    class ToModelList {

        @Test
        @DisplayName("should map dto list using default interface method")
        void shouldMapDtoListToModelListUsingDefaultInterfaceMethod() {
            PortionDto first = new PortionDto();
            first.setName("Maca");
            first.setServingQuantity(1);
            first.setServingUnit("unit");
            first.setCaloriesPerServing(52);

            PortionDto second = new PortionDto();
            second.setName("Leite Desnatado");
            second.setServingQuantity(100);
            second.setServingUnit("ml");
            second.setCaloriesPerServing(60);

            List<Portion> mapped = mapper.toModelList(List.of(first, second));

            assertAll(
                    () -> assertEquals(2, mapped.size()),
                    () -> assertEquals("Maca", mapped.getFirst().getName()),
                    () -> assertEquals("maca", mapped.getFirst().getNormalizedName()),
                    () -> assertEquals(52, mapped.getFirst().getCaloriesPerServing()),
                    () -> assertEquals("Leite Desnatado", mapped.get(1).getName()),
                    () -> assertEquals("leite desnatado", mapped.get(1).getNormalizedName()),
                    () -> assertEquals("ml", mapped.get(1).getServingUnit())
            );
        }
    }
}


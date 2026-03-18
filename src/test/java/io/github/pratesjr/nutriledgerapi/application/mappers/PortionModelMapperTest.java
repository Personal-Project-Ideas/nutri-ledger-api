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
            dto.setFoodName("Rice");
            dto.setCaloriesPerPortion(130);
            dto.setPortionGrams(100);
            dto.setPortionQuantity(1);
            dto.setPortionMls(null);

            Portion mapped = mapper.toModel(dto);

            assertAll(
                    () -> assertEquals("Rice", mapped.getFoodName()),
                    () -> assertEquals(130, mapped.getCaloriesPerPortion()),
                    () -> assertEquals(100, mapped.getPortionGrams()),
                    () -> assertEquals(1, mapped.getPortionQuantity()),
                    () -> assertNull(mapped.getPortionMls())
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
            first.setFoodName("Apple");
            first.setCaloriesPerPortion(52);

            PortionDto second = new PortionDto();
            second.setFoodName("Milk");
            second.setCaloriesPerPortion(60);
            second.setPortionMls(100);

            List<Portion> mapped = mapper.toModelList(List.of(first, second));

            assertAll(
                    () -> assertEquals(2, mapped.size()),
                    () -> assertEquals("Apple", mapped.getFirst().getFoodName()),
                    () -> assertEquals(52, mapped.getFirst().getCaloriesPerPortion()),
                    () -> assertEquals("Milk", mapped.get(1).getFoodName()),
                    () -> assertEquals(100, mapped.get(1).getPortionMls())
            );
        }
    }
}


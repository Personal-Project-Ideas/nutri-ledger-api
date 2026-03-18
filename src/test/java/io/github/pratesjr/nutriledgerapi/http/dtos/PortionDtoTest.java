package io.github.pratesjr.nutriledgerapi.http.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PortionDtoTest {

    @Nested
    @DisplayName("constructor")
    class Constructor {

        @Test
        @DisplayName("should create dto with expected default values")
        void shouldCreateDtoWithExpectedDefaultValues() {
            PortionDto dto = new PortionDto();

            assertAll(
                    () -> assertEquals("", dto.getFoodName()),
                    () -> assertEquals(0, dto.getCaloriesPerPortion()),
                    () -> assertNull(dto.getPortionGrams()),
                    () -> assertNull(dto.getPortionQuantity()),
                    () -> assertNull(dto.getPortionMls())
            );
        }
    }

    @Nested
    @DisplayName("getter and setter")
    class LombokGeneratedMethods {

        @Test
        @DisplayName("should support getters setters equals hashCode and toString")
        void shouldSupportGettersSettersEqualsHashCodeAndToString() {
            PortionDto first = new PortionDto();
            first.setFoodName("Banana");
            first.setCaloriesPerPortion(89);
            first.setPortionGrams(100);
            first.setPortionQuantity(1);
            first.setPortionMls(null);

            PortionDto second = new PortionDto();
            second.setFoodName("Banana");
            second.setCaloriesPerPortion(89);
            second.setPortionGrams(100);
            second.setPortionQuantity(1);
            second.setPortionMls(null);

            assertAll(
                    () -> assertEquals("Banana", first.getFoodName()),
                    () -> assertEquals(89, first.getCaloriesPerPortion()),
                    () -> assertEquals(first, second),
                    () -> assertEquals(first.hashCode(), second.hashCode()),
                    () -> assertTrue(first.toString().contains("foodName=Banana"))
            );

            second.setCaloriesPerPortion(90);
            assertNotEquals(first, second);
        }
    }
}


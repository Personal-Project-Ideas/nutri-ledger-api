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
                    () -> assertEquals("", dto.getName()),
                    () -> assertNull(dto.getServingQuantity()),
                    () -> assertNull(dto.getServingUnit()),
                    () -> assertNull(dto.getCaloriesPerServing())
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
            first.setName("Banana");
            first.setServingQuantity(1);
            first.setServingUnit("unit");
            first.setCaloriesPerServing(89);

            PortionDto second = new PortionDto();
            second.setName("Banana");
            second.setServingQuantity(1);
            second.setServingUnit("unit");
            second.setCaloriesPerServing(89);

            assertAll(
                    () -> assertEquals("Banana", first.getName()),
                    () -> assertEquals(89, first.getCaloriesPerServing()),
                    () -> assertEquals(first, second),
                    () -> assertEquals(first.hashCode(), second.hashCode()),
                    () -> assertTrue(first.toString().contains("name=Banana"))
            );

            second.setCaloriesPerServing(90);
            assertNotEquals(first, second);
        }
    }
}


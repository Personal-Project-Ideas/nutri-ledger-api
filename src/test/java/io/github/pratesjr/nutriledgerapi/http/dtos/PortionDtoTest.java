package io.github.pratesjr.nutriledgerapi.http.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortionDtoTest {

    @Nested
    @DisplayName("constructor")
    class Constructor {

        @Test
        @DisplayName("should create dto with expected values")
        void shouldCreateDtoWithExpectedValues() {
            PortionDto dto = new PortionDto("Banana", 1, "unit", 89);

            assertAll(
                    () -> assertEquals("Banana", dto.name()),
                    () -> assertEquals(1, dto.servingQuantity()),
                    () -> assertEquals("unit", dto.servingUnit()),
                    () -> assertEquals(89, dto.caloriesPerServing())
            );
        }

        @Test
        @DisplayName("should support equals hashCode and toString")
        void shouldSupportEqualsHashCodeAndToString() {
            PortionDto first = new PortionDto("Banana", 1, "unit", 89);
            PortionDto second = new PortionDto("Banana", 1, "unit", 89);
            PortionDto third = new PortionDto("Banana", 1, "unit", 90);

            assertAll(
                    () -> assertEquals(first, second),
                    () -> assertEquals(first.hashCode(), second.hashCode()),
                    () -> assertTrue(first.toString().contains("Banana")),
                    () -> assertNotEquals(first, third)
            );
        }
    }
}

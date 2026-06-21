package io.github.pratesjr.nutriledgerapi.http.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PortionDto(
        @NotBlank String name,
        @Positive Integer servingQuantity,
        @NotBlank String servingUnit,
        @Positive Integer caloriesPerServing
) {}

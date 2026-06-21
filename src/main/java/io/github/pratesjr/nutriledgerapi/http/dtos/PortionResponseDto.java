package io.github.pratesjr.nutriledgerapi.http.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PortionResponseDto(
        UUID id,
        String name,
        Integer servingQuantity,
        String servingUnit,
        Integer caloriesPerServing,
        String createdAt,
        String updatedAt
) {}

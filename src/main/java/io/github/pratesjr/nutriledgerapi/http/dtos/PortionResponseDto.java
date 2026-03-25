package io.github.pratesjr.nutriledgerapi.http.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PortionResponseDto {

    private UUID id;
    private String name = "";
    private Integer servingQuantity;
    private String servingUnit;
    private Integer caloriesPerServing;
    private Instant createdAt;
    private Instant updatedAt;
}


package io.github.pratesjr.nutriledgerapi.http.dtos;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PortionDto {
    @Nonnull @NotBlank
    private String name;


    @Positive @NotBlank
    private Integer servingQuantity;

    @Nonnull @NotBlank
    private String servingUnit;

    @Nonnull @Positive @NotBlank
    private Integer caloriesPerServing;
}

package io.github.pratesjr.nutriledgerapi.http.dtos;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PortionDto {
    @Nonnull
    private String foodName = "";

    @Nonnull
    private Integer caloriesPerPortion = 0;

    @Nullable
    private Integer portionGrams;

    @Nullable
    private Integer portionQuantity;

    @Nullable
    private Integer portionMls;
}

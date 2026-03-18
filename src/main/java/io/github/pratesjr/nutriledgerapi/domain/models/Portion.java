package io.github.pratesjr.nutriledgerapi.domain.models;

import java.time.Instant;
import java.util.UUID;

public class Portion {

    private final UUID id;
    private final String foodName;
    private final Integer caloriesPerPortion;
    private final Integer portionGrams;
    private final Integer portionQuantity;
    private final Integer portionMls;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Portion(String foodName, Integer caloriesPerPortion, Integer portionGrams,
                   Integer portionQuantity, Integer portionMls) {
        this(null, foodName, caloriesPerPortion, portionGrams, portionQuantity, portionMls, null, null);
    }

    public Portion(UUID id, String foodName, Integer caloriesPerPortion, Integer portionGrams,
                   Integer portionQuantity, Integer portionMls, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.foodName = foodName;
        this.caloriesPerPortion = caloriesPerPortion;
        this.portionGrams = portionGrams;
        this.portionQuantity = portionQuantity;
        this.portionMls = portionMls;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getFoodName() {
        return foodName;
    }

    public Integer getCaloriesPerPortion() {
        return caloriesPerPortion;
    }

    public Integer getPortionGrams() {
        return portionGrams;
    }

    public Integer getPortionQuantity() {
        return portionQuantity;
    }

    public Integer getPortionMls() {
        return portionMls;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public boolean hasGramsMeasure() {
        return portionGrams != null && portionGrams > 0;
    }

    public boolean hasQuantityMeasure() {
        return portionQuantity != null && portionQuantity > 0;
    }

    public boolean hasVolumeMeasure() {
        return portionMls != null && portionMls > 0;
    }
}



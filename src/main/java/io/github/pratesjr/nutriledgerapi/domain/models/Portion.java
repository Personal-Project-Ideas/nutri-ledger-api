package io.github.pratesjr.nutriledgerapi.domain.models;

public class Portion {

    private final String foodName;
    private final Integer caloriesPerPortion;
    private final Integer portionGrams;
    private final Integer portionQuantity;
    private final Integer portionMls;

    public Portion(String foodName, Integer caloriesPerPortion, Integer portionGrams,
                   Integer portionQuantity, Integer portionMls) {
        this.foodName = foodName;
        this.caloriesPerPortion = caloriesPerPortion;
        this.portionGrams = portionGrams;
        this.portionQuantity = portionQuantity;
        this.portionMls = portionMls;
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



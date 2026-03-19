package io.github.pratesjr.nutriledgerapi.domain.models;

import java.time.Instant;
import java.text.Normalizer;
import java.util.Locale;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Portion {

    private final UUID id;
    private final String name;
    private final String normalizedName;
    private final Integer servingQuantity;
    private final String servingUnit;
    private final Integer caloriesPerServing;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Portion(String name, Integer servingQuantity,
                   String servingUnit, Integer caloriesPerServing) {
        this(null, name, servingQuantity, servingUnit, caloriesPerServing, null, null);
    }

    public Portion(UUID id, String name, Integer servingQuantity,
                   String servingUnit, Integer caloriesPerServing, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.normalizedName = normalizeName(name);
        this.servingQuantity = servingQuantity;
        this.servingUnit = servingUnit;
        this.caloriesPerServing = caloriesPerServing;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private String normalizeName(String value) {
        if (value == null) {
            return "";
        }

        String trimmedLower = value.trim().toLowerCase(Locale.ROOT);
        String withoutAccents = Normalizer.normalize(trimmedLower, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");
        return withoutAccents.replaceAll("\\s+", " ");
    }


    public boolean hasServingQuantity() {
        return servingQuantity != null && servingQuantity > 0;
    }
}



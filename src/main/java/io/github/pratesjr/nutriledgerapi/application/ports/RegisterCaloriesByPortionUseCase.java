package io.github.pratesjr.nutriledgerapi.application.ports;

import io.github.pratesjr.nutriledgerapi.domain.models.Portion;

public interface RegisterCaloriesByPortionUseCase {
     Object process (Portion data);
}


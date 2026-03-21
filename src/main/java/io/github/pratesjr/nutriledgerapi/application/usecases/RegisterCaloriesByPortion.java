package io.github.pratesjr.nutriledgerapi.application.usecases;

import io.github.pratesjr.nutriledgerapi.application.ports.RegisterCaloriesByPortionUseCase;
import io.github.pratesjr.nutriledgerapi.domain.models.Portion;
import org.springframework.stereotype.Service;

@Service
public class RegisterCaloriesByPortion implements RegisterCaloriesByPortionUseCase {

    @Override
    public Portion process(Portion data) {
        // Placeholder until persistence/orchestration is implemented.
        return data;
    }
}

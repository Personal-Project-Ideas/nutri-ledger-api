package io.github.pratesjr.nutriledgerapi.application.usecases;

import io.github.pratesjr.nutriledgerapi.application.ports.RegisterCaloriesByPortionUseCase;
import io.github.pratesjr.nutriledgerapi.domain.models.Portion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterCaloriesByPortion implements RegisterCaloriesByPortionUseCase {

//    @Autowired
//    public RegisterCaloriesByPortion(/* dependências */) {
//        // ...existing code...
//    }

    @Override
    public Portion process(Portion data) {
        return data;
    }
}

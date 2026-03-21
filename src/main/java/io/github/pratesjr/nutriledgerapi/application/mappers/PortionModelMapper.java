package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.Portion;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionDto;
import org.springframework.stereotype.Component;

@Component
public class PortionModelMapper implements ModelMapper<PortionDto, Portion> {

    @Override
    public Portion toModel(PortionDto dto) {
        if (dto == null) {
            return null;
        }

        return new Portion(
                dto.getName(),
                dto.getServingQuantity(),
                dto.getServingUnit(),
                dto.getCaloriesPerServing()
        );
    }
}


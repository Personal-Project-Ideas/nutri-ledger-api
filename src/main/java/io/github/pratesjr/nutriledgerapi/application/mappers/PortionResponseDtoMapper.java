package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.Portion;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class PortionResponseDtoMapper implements DtoMapper<Portion, PortionResponseDto> {

    @Override
    public PortionResponseDto toDto(Portion model) {
        if (model == null) {
            return null;
        }

        PortionResponseDto dto = new PortionResponseDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setServingQuantity(model.getServingQuantity());
        dto.setServingUnit(model.getServingUnit());
        dto.setCaloriesPerServing(model.getCaloriesPerServing());
        dto.setCreatedAt(model.getCreatedAt() != null ? model.getCreatedAt().toString() : null);
        dto.setUpdatedAt(model.getUpdatedAt() != null ? model.getUpdatedAt().toString() : null);
        return dto;
    }
}

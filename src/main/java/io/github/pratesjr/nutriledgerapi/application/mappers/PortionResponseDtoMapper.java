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
        dto.setFoodName(model.getFoodName());
        dto.setCaloriesPerPortion(model.getCaloriesPerPortion());
        dto.setPortionGrams(model.getPortionGrams());
        dto.setPortionQuantity(model.getPortionQuantity());
        dto.setPortionMls(model.getPortionMls());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        return dto;
    }
}


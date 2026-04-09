package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.Portion;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PortionMapper {
    // PortionDto -> Portion
    default Portion toModel(PortionDto dto) {
        if (dto == null) return null;
        return new Portion(
            dto.getName(),
            dto.getServingQuantity(),
            dto.getServingUnit(),
            dto.getCaloriesPerServing()
        );
    }

    // Portion -> PortionDto
    @Mapping(source = "name", target = "name")
    @Mapping(source = "servingQuantity", target = "servingQuantity")
    @Mapping(source = "servingUnit", target = "servingUnit")
    @Mapping(source = "caloriesPerServing", target = "caloriesPerServing")
    PortionDto toDto(Portion model);
}

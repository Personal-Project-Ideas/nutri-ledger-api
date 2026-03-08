package io.github.pratesjr.nutriledgerapi.application.mappers;

import java.util.List;

public interface DtoMapper<MODEL, DTO> {

    DTO toDto(MODEL model);

    default List<DTO> toDtoList(List<MODEL> models) {
        return models.stream().map(this::toDto).toList();
    }
}


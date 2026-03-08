package io.github.pratesjr.nutriledgerapi.application.mappers;

import java.util.List;

public interface ModelMapper<DTO, MODEL> {

    MODEL toModel(DTO dto);

    default List<MODEL> toModelList(List<DTO> dtos) {
        return dtos.stream().map(this::toModel).toList();
    }
}


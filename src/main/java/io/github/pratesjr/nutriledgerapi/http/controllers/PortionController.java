package io.github.pratesjr.nutriledgerapi.http.controllers;

import io.github.pratesjr.nutriledgerapi.application.mappers.PortionModelMapper;
import io.github.pratesjr.nutriledgerapi.application.mappers.PortionResponseDtoMapper;
import io.github.pratesjr.nutriledgerapi.application.ports.RegisterCaloriesByPortionUseCase;
import io.github.pratesjr.nutriledgerapi.domain.models.Portion;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionDto;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portions")
public class PortionController {

	private final PortionModelMapper portionModelMapper;
	private final PortionResponseDtoMapper portionResponseDtoMapper;
	private final RegisterCaloriesByPortionUseCase registerCaloriesByPortionUseCase;

	public PortionController(PortionModelMapper portionModelMapper,
			PortionResponseDtoMapper portionResponseDtoMapper,
			RegisterCaloriesByPortionUseCase registerCaloriesByPortionUseCase) {
		this.portionModelMapper = portionModelMapper;
		this.portionResponseDtoMapper = portionResponseDtoMapper;
		this.registerCaloriesByPortionUseCase = registerCaloriesByPortionUseCase;
	}

	@PostMapping
	public ResponseEntity<PortionResponseDto> createPortion(@RequestBody PortionDto portionDto) {
		Portion portion = portionModelMapper.toModel(portionDto);
		Portion registeredPortion = registerCaloriesByPortionUseCase.process(portion);
		PortionResponseDto response = portionResponseDtoMapper.toDto(registeredPortion);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}

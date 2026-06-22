package io.github.pratesjr.nutriledgerapi.http.controllers;

import io.github.pratesjr.nutriledgerapi.application.mappers.PortionMapper;
import io.github.pratesjr.nutriledgerapi.application.mappers.ResponseMapper;
import io.github.pratesjr.nutriledgerapi.application.ports.RegisterCaloriesByPortionUseCase;
import io.github.pratesjr.nutriledgerapi.domain.models.Portion;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionDto;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Portions", description = "Portion management endpoints")
@RestController
@RequestMapping("/portions")
public class PortionController {

	private final PortionMapper portionMapper;
	private final ResponseMapper responseMapper;
	private final RegisterCaloriesByPortionUseCase registerCaloriesByPortionUseCase;

	public PortionController(PortionMapper portionMapper, ResponseMapper responseMapper,
			RegisterCaloriesByPortionUseCase registerCaloriesByPortionUseCase) {
		this.portionMapper = portionMapper;
		this.responseMapper = responseMapper;
		this.registerCaloriesByPortionUseCase = registerCaloriesByPortionUseCase;
	}


	@Operation(
			summary = "Create a new portion",
			description = "Registers a new portion with its calorie information",
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Portion created successfully",
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(implementation = PortionResponseDto.class)
							)
					),
			}
	)
	@PostMapping()
	public ResponseEntity<PortionResponseDto> createPortion(@Valid @RequestBody PortionDto portionDto) {
		Portion portion = portionMapper.toModel(portionDto);
		Portion registeredPortion = registerCaloriesByPortionUseCase.process(portion);
		PortionResponseDto response = responseMapper.toPortionResponseDto(registeredPortion);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}

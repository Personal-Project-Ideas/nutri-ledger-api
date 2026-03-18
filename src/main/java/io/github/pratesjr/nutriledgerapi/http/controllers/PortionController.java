package io.github.pratesjr.nutriledgerapi.http.controllers;

import io.github.pratesjr.nutriledgerapi.application.mappers.PortionModelMapper;
import io.github.pratesjr.nutriledgerapi.domain.models.Portion;
import io.github.pratesjr.nutriledgerapi.http.dtos.PortionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portions")
public class PortionController {

	private final PortionModelMapper portionModelMapper;

	public PortionController(PortionModelMapper portionModelMapper) {
		this.portionModelMapper = portionModelMapper;
	}

	@PostMapping
	public ResponseEntity<PortionDto> createPortion(@RequestBody PortionDto portionDto) {
		Portion portion = portionModelMapper.toModel(portionDto);

		// Temporary response until use case orchestration is implemented.
		PortionDto response = new PortionDto();
		response.setFoodName(portion.getFoodName());
		response.setCaloriesPerPortion(portion.getCaloriesPerPortion());
		response.setPortionGrams(portion.getPortionGrams());
		response.setPortionQuantity(portion.getPortionQuantity());
		response.setPortionMls(portion.getPortionMls());

		return ResponseEntity.ok(response);
	}
}

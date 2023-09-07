package com.sottie.app.sample.rest;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.sample.model.Sample;
import com.sottie.app.sample.service.SampleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Sample Controller", description = "Sample API 입니다.")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/sample")
public class SampleController {

	private final SampleService sampleService;

	@Operation(summary = "api summary", description = "api description")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK !!", content = @Content(schema = @Schema(implementation = Sample.class))),
		@ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
		@ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
	})
	@GetMapping(value = "test")
	public ResponseEntity<Sample> test() {
		Optional<Sample> sample = sampleService.test();
		return new ResponseEntity<>(sample.get(), HttpStatus.OK);
	}
}

package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.application.AddGatheringService;
import com.sottie.app.gathering.model.Gathering;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AddGatheringController {

	private final AddGatheringService addGatheringService;

	@PostMapping("/sottie/gathering")
	public ResponseEntity<Gathering> addGathering(@RequestBody @Valid DefaultGatheringRequest defaultGatheringRequest) {
		Gathering result = addGatheringService.addGathering(defaultGatheringRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

}

package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.application.ExitGatheringService;
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
class ExitGatheringController {

	private final ExitGatheringService exitGatheringService;

	@PostMapping("/sottie/gathering/exit")
	public ResponseEntity exitGathering(@RequestBody @Valid DefaultGatheringRequest defaultGatheringRequest) {
		exitGatheringService.exitGathering(defaultGatheringRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}

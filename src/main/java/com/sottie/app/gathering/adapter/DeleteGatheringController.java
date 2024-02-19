package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.application.AddGatheringService;
import com.sottie.app.gathering.application.DeleteGatheringService;
import com.sottie.app.gathering.model.Gathering;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class DeleteGatheringController {

	private final DeleteGatheringService deleteGatheringService;

	@DeleteMapping("/sottie/gathering")
	public void deleteGathering(@RequestBody @Valid DefaultGatheringRequest defaultGatheringRequest) {
		deleteGatheringService.deleteGathering(defaultGatheringRequest.to());
		return;
	}

}

package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.application.GetGatheringService;
import com.sottie.app.gathering.model.Gathering;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
class GetGatheringController {

	private final GetGatheringService getGatheringService;

	@GetMapping("/sottie/gatherings")
	public ResponseEntity<List<Gathering>> getGatherings (@RequestBody @Valid DefaultGatheringRequest defaultGatheringRequest) {
		List<Gathering> result = getGatheringService.getGatherings(
				defaultGatheringRequest.gatheringCategory(),
				defaultGatheringRequest.title(),
				defaultGatheringRequest.locationId(),
				defaultGatheringRequest.peopleNum(),
				defaultGatheringRequest.genderRestriction(),
				defaultGatheringRequest.mannerRestrictionYn(),
				defaultGatheringRequest.ageRestrictionYn());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}

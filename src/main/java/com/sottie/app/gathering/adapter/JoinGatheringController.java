package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.application.JoinGatheringService;
import com.sottie.app.gathering.model.dto.GatheringDto;
import com.sottie.app.gathering.model.record.JoinGatheringRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class JoinGatheringController {

	private final JoinGatheringService joinGatheringService;

	@PostMapping("/sottie/gathering/join")
	public ResponseEntity<GatheringDto> joinGathering(@RequestBody @Valid JoinGatheringRequest joinGatheringRequest) {
		GatheringDto result = joinGatheringService.joinGathering(joinGatheringRequest);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}

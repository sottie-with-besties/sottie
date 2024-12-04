package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.application.GetGatheringService;
import com.sottie.app.gathering.model.record.DefaultGatheringRequest;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.record.GetGatheringRequest;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
class GetGatheringsController {

	private final GetGatheringService getGatheringService;

	// TODO Response DTO 로 변경하기
	@GetMapping("/sottie/gatherings")
	public ResponseEntity<List<Gathering>> getGatherings (@RequestBody @Nullable GetGatheringRequest getGatheringRequest) {
		List<Gathering> result = getGatheringService.getGatherings(getGatheringRequest);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
class GetGatheringsController {

	private final GetGatheringService getGatheringService;

	// TODO 왜 파라미터 null 로 들어오는지 확인해야함
	@GetMapping("/sottie/gatherings")
	public ResponseEntity<List<Gathering>> getGatherings (@RequestBody @Valid @Nullable GetGatheringRequest getGatheringRequest) {
		List<Gathering> result = getGatheringService.getGatherings(getGatheringRequest);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}

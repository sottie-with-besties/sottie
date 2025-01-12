package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.application.GetGatheringService;
import com.sottie.app.gathering.model.dto.GatheringDto;
import com.sottie.app.gathering.model.record.GetGatheringRequest;
import jakarta.annotation.Nullable;
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
	public ResponseEntity<List<GatheringDto>> getGatherings (@RequestBody @Nullable GetGatheringRequest getGatheringRequest) {
		List<GatheringDto> result = getGatheringService.getGatherings(getGatheringRequest);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("sottie/gatherings/{userId}")
	public ResponseEntity<List<GatheringDto>> getJoinedGatherings (@PathVariable Long userId) {
		List<GatheringDto> result = getGatheringService.getJoinedGatherings(userId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}

package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.application.DeleteGatheringService;
import com.sottie.app.gathering.model.dto.GatheringDto;
import com.sottie.app.gathering.model.record.DeleteGatheringRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class DeleteGatheringController {

	private final DeleteGatheringService deleteGatheringService;

	@DeleteMapping("/sottie/gathering")
	public ResponseEntity<GatheringDto> deleteGathering(@RequestBody @Valid DeleteGatheringRequest deleteGatheringRequest) {
		GatheringDto result = deleteGatheringService.deleteGathering(deleteGatheringRequest);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}

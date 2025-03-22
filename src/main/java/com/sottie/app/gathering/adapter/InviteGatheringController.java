package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.application.InviteGatheringService;
import com.sottie.app.gathering.model.dto.GatheringInvitationDto;
import com.sottie.app.gathering.model.record.InviteGatheringRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class InviteGatheringController {

	private final InviteGatheringService inviteGatheringService;

	@PostMapping("/sottie/gathering/invite")
	public ResponseEntity<GatheringInvitationDto> inviteGathering(@RequestBody @Valid InviteGatheringRequest inviteGatheringRequest) {
		GatheringInvitationDto result = inviteGatheringService.inviteGathering(inviteGatheringRequest);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}

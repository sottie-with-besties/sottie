package com.sottie.app.gathering.model.record;

import com.sottie.app.gathering.model.InvitationStatusCategory;
import lombok.Builder;


@Builder
public record ReactInviteGatheringRequest(
	Long gatheringId,

	// 나를 초대한 친구
	Long friendUserId,
	InvitationStatusCategory invitationStatus
	) {

}

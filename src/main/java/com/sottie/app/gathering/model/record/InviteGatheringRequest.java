package com.sottie.app.gathering.model.record;

import lombok.Builder;


@Builder
public record InviteGatheringRequest(
	Long gatheringId,

	// 초대 대상 친구
	Long friendUserId
	) {

}

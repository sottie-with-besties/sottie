package com.sottie.app.gathering.model.record;

import lombok.Builder;


@Builder
public record InviteGatheringRequest(
	Long gatheringId,
	Long friendUserId
	) {

}

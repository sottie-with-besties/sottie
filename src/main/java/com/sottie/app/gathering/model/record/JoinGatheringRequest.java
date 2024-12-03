package com.sottie.app.gathering.model.record;

import lombok.Builder;


@Builder
public record JoinGatheringRequest(
	Long id,
	Long userId
	) {

}

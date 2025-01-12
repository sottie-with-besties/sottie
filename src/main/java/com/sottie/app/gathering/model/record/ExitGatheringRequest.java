package com.sottie.app.gathering.model.record;

import lombok.Builder;


@Builder
public record ExitGatheringRequest(
        Long gatheringId,
        Long userId
        ) {

}

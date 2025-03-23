package com.sottie.app.friend.model.record;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public
record DefaultFriendRequest(
	@NotNull
	Long userId,

	@NotNull
	Long FriendId

) {
}

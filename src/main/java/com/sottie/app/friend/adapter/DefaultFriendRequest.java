package com.sottie.app.friend.adapter;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
record DefaultFriendRequest(
	@NotNull
	Long userId,

	@NotNull
	Long FriendId

) {
}

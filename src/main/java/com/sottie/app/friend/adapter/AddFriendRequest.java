package com.sottie.app.friend.adapter;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
record AddFriendRequest(
	@NotNull
	Long userId,

	@NotNull
	Long FriendId

) {
}

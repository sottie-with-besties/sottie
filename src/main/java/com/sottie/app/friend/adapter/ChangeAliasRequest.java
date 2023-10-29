package com.sottie.app.friend.adapter;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
record ChangeAliasRequest(
	@NotNull
	Long userId,

	@NotNull
	Long FriendId,

	@NotNull
	String alias

) {
}

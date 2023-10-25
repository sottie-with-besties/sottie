package com.sottie.app.friend.model;

import lombok.Builder;

@Builder
public record FriendProfile(
	String alias,

	String photo,

	String moodStatus,

	String introPhrase

) {
}

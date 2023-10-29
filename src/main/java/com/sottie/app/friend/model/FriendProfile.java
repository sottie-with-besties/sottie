package com.sottie.app.friend.model;

import lombok.Builder;

@Builder
public record FriendProfile(

	Long userId,

	String alias,

	String photo,

	String moodStatus,

	String introPhrase

) {
}

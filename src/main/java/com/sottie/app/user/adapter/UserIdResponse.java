package com.sottie.app.user.adapter;

import com.sottie.app.user.model.User;

import lombok.Builder;

@Builder
record UserIdResponse(

	String userId

) {
	public static UserIdResponse from(User user) {
		return UserIdResponse.builder()
			.userId(user.getEmail())
			.build();
	}
}

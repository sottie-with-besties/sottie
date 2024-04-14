package com.sottie.app.user.adapter;

import com.sottie.app.user.model.User;

import lombok.Builder;

@Builder
record UserEmailResponse(

	String email

) {
	public static UserEmailResponse from(User user) {
		return UserEmailResponse.builder()
			.email(user.getEmail())
			.build();
	}
}

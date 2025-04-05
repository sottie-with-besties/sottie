package com.sottie.app.user.model.record;

import com.sottie.app.user.model.User;

import lombok.Builder;

@Builder
public record UserEmailResponse(

	String email

) {
	public static UserEmailResponse from(User user) {
		return UserEmailResponse.builder()
			.email(user.getEmail())
			.build();
	}
}

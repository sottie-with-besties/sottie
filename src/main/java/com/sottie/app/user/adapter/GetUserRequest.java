package com.sottie.app.user.adapter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
record GetUserRequest(
	@NotBlank
	@Email
	String email,
	@NotBlank
	String password) {

}

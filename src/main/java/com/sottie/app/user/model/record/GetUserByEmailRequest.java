package com.sottie.app.user.model.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record GetUserByEmailRequest(

	@NotBlank
	@Email
	String email

) {
}

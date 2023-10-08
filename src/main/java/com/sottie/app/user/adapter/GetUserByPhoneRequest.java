package com.sottie.app.user.adapter;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
record GetUserByPhoneRequest(

	@NotBlank
	String phoneNumber

) {
}

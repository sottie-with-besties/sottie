package com.sottie.app.user.adapter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
record GetUserByPhoneRequest(
	@Size(min = 11) //01012345678 -> 최소 11자리 이상 이어야 함
	@NotBlank
	String phoneNumber

) {
}

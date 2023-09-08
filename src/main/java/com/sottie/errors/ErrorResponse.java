package com.sottie.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

	private final String code;
	private final String description;

	public static ErrorResponse of(String code, String description) {
		return ErrorResponse.builder()
			.code(code)
			.description(description)
			.build();
	}
}

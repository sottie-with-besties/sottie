package com.sottie.app.user.error;

import org.springframework.http.HttpStatus;

import com.sottie.errors.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

	USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "User Unauthorized"),
	;
	private final HttpStatus httpStatus;

	private final String message;

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}

}

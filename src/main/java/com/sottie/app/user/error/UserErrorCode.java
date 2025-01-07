package com.sottie.app.user.error;

import org.springframework.http.HttpStatus;

import com.sottie.errors.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

	USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "User Unauthorized"),
	USER_ALREADY_EXISTS(HttpStatus.INTERNAL_SERVER_ERROR, "User already exists"),
	CANNOT_FIND_USER(HttpStatus.INTERNAL_SERVER_ERROR, "Can not find user")
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

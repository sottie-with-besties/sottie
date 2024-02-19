package com.sottie.app.gathering.error;

import com.sottie.errors.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GatheringErrorCode implements ErrorCode {

	GATHERING_INSUFFICIENT_INFORMATION(HttpStatus.INTERNAL_SERVER_ERROR, "Gathering insufficient information"),

	NOT_HOST_USER(HttpStatus.INTERNAL_SERVER_ERROR, "not host user")
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

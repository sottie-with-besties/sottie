package com.sottie.app.review.error;

import com.sottie.errors.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {

	REVIEW_INSUFFICIENT_INFORMATION(HttpStatus.INTERNAL_SERVER_ERROR, "Review insufficient information"),

	INVALID_REVIEWER(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Reviewer"),

	INVALID_TARGET_USER(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Target User"),

	NOT_REVIEW_TIME(HttpStatus.INTERNAL_SERVER_ERROR, "This is not available review time."),
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

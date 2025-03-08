package com.sottie.app.cash.error;

import com.sottie.errors.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CashErrorCode implements ErrorCode {

	INSUFFICIENT_TOTAL_CASH(HttpStatus.INTERNAL_SERVER_ERROR, "Insufficient Total Cash"),
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

package com.sottie.errors;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum CommonErrorCode implements ErrorCode {
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter is included"),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Request is unauthorized"),
	FORBIDDEN(HttpStatus.FORBIDDEN, "Request is forbidden"),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Cannot find resource."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Request method is not supported."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
	MISSING_HEADER(HttpStatus.BAD_REQUEST, "Necessary header is missing or blank."),
	RESOURCE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Resource already exists."),
	;

	private final HttpStatus httpStatus;
	private final String message;
}

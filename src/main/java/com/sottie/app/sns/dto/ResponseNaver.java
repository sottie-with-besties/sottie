package com.sottie.app.sns.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseNaver<T> {
	private String resultcode;
	private String message;
	private T response;
}

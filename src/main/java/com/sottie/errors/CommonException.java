package com.sottie.errors;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

	protected final ErrorCode errorCode;

	protected CommonException(final Throwable cause, final ErrorCode errorCode) {
		super(errorCode.getMessage(), cause);
		this.errorCode = errorCode;
	}

	public static CommonExceptionBuilder builder(final ErrorCode errorCode) {
		return new CommonExceptionBuilder(errorCode);
	}

	public static CommonExceptionBuilder builder(final ErrorCode errorCode, final Exception cause) {
		return new CommonExceptionBuilder(errorCode, cause);
	}

	public static class CommonExceptionBuilder {
		private Exception e;
		private final ErrorCode errorCode;

		private CommonExceptionBuilder(final ErrorCode errorCode) {
			this.errorCode = errorCode;
		}

		private CommonExceptionBuilder(final ErrorCode errorCode, final Exception cause) {
			this.errorCode = errorCode;
			this.e = cause;
		}

		public CommonExceptionBuilder e(final Exception e) {
			this.e = e;
			return this;
		}

		public CommonException build() {
			return new CommonException(this.e, this.errorCode);
		}
	}

}

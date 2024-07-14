package com.sottie.app.user.adapter;

import com.sottie.app.user.model.Gender;
import com.sottie.app.user.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
record DefaultUserRequest(

	String name,
	String phoneNumber,
	Gender gender,
	String identifier,
	String birthYear,
	boolean phoneAuthenticated,
	@NotBlank
	@Email
	String email,
	@NotBlank
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,}$",
		message = "비밀번호는 영문과 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 이상의 비밀번호여야 합니다.")
	String password) {

	User to() {
		return User.builder()
				.name(this.name)
				.phoneNumber(this.phoneNumber)
				.gender(this.gender)
				.birthYear(this.birthYear)
				.identifier(this.identifier)
				.phoneAuthenticated(this.phoneAuthenticated)
				.email(this.email)
				.password(this.password)
				.build();
	}
}

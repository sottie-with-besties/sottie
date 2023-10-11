package com.sottie.app.user.adapter;

import static jakarta.validation.Validation.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class DefaultUserRequestTest {
	@ParameterizedTest
	@MethodSource("invalidBody")
	void 생성자테스트_유효하지않음(String email, String password) {
		ValidatorFactory validatorFactory = buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();

		DefaultUserRequest defaultUserRequest = DefaultUserRequest.builder()
			.email(email)
			.password(password)
			.build();

		Set<ConstraintViolation<DefaultUserRequest>> violations = validator.validate(defaultUserRequest);

		assertThat(!violations.isEmpty()).isEqualTo(true);
	}

	@ParameterizedTest
	@MethodSource("validBody")
	void 생성자테스트_유효함(String email, String password) {
		ValidatorFactory validatorFactory = buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();

		DefaultUserRequest defaultUserRequest = DefaultUserRequest.builder()
			.email(email)
			.password(password)
			.build();

		Set<ConstraintViolation<DefaultUserRequest>> violations = validator.validate(defaultUserRequest);

		assertThat(violations.isEmpty()).isEqualTo(true);
	}

	private static List<Arguments> invalidBody() {
		return List.of(
			Arguments.of("abc.com", "password"), //invalid email
			Arguments.of("", " "),  //blank test
			Arguments.of("abc@com", "test123") //invalid password
		);
	}

	private static List<Arguments> validBody() {
		return List.of(
			Arguments.of("test@abc.com", "password213!@"),
			Arguments.of("abc@gmail.com", "!!!1234test"),
			Arguments.of("abc@com", "test123#@!")
		);
	}

}

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

class GetUserByEmailRequestTest {

	@ParameterizedTest
	@MethodSource("invalidBody")
	void 생성자테스트_유효하지않음(String email) {
		ValidatorFactory validatorFactory = buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();

		GetUserByEmailRequest getUserByEmailRequest = GetUserByEmailRequest.builder()
			.email(email)
			.build();

		Set<ConstraintViolation<GetUserByEmailRequest>> violations = validator.validate(getUserByEmailRequest);

		assertThat(!violations.isEmpty()).isEqualTo(true);
	}

	private static List<Arguments> invalidBody() {
		return List.of(
			Arguments.of("abc.com"), //invalid email
			Arguments.of(""),  //blank test
			Arguments.of(" ") //empty test
		);
	}

}

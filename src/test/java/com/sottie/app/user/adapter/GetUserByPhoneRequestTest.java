package com.sottie.app.user.adapter;

import static jakarta.validation.Validation.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
class GetUserByPhoneRequestTest {
	@ParameterizedTest
	@MethodSource("invalidBody")
	void 생성자테스트_유효하지않음(String phoneNumber) {
		ValidatorFactory validatorFactory = buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();

		GetUserByPhoneRequest getUserByPhoneRequest = GetUserByPhoneRequest.builder()
			.phoneNumber(phoneNumber)
			.build();

		Set<ConstraintViolation<GetUserByPhoneRequest>> violations = validator.validate(getUserByPhoneRequest);

		assertThat(!violations.isEmpty()).isEqualTo(true);
	}

	private static List<Arguments> invalidBody() {
		return List.of(
			Arguments.of("0106543123"), //size invalid
			Arguments.of(""),  //blank test
			Arguments.of(" ") //empty test
		);
	}

}

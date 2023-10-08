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
public class SignupUserRequestTest {
	@ParameterizedTest
	@MethodSource("validBody")
	void 생성자테스트(String email, String password) {
		ValidatorFactory validatorFactory = buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();

		DefaultUserRequest defaultUserRequest = DefaultUserRequest.builder()
			.email(email)
			.password(password)
			.build();

		Set<ConstraintViolation<DefaultUserRequest>> violations = validator.validate(defaultUserRequest);

		assertThat(violations.size() == 0).isEqualTo(true);
	}

	private static List<Arguments> validBody() {
		return List.of(
			Arguments.of("test@abc.com", "password213!@"), //invalid email
			Arguments.of("abc@gmail.com", "!!!1234test"),  //blank test
			Arguments.of("abc@com", "test123#@!") //invalid password
		);
	}
}

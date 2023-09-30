package com.sottie.app.user.adapter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sottie.app.user.application.AddUserService;
import com.sottie.errors.GlobalExceptionHandler;

@ExtendWith(MockitoExtension.class)
public class AddUserControllerTest {

	@InjectMocks
	private AddUserController controller;

	@Mock
	private AddUserService service;

	private MockMvc mockMvc;

	private String url;

	@BeforeEach
	void init() {
		this.url = "/sottie/signup/users";
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
			.setControllerAdvice(new GlobalExceptionHandler())
			.setValidator(new LocalValidatorFactoryBean())
			.build();
	}

	@ParameterizedTest
	@MethodSource("invalidBody")
	void 사용자추가실패_invalidBody(String email, String password) throws Exception {
		//given
		SignUpUserRequest signUpUserRequest = SignUpUserRequest.builder()
			.email(email)
			.password(password)
			.build();

		//when
		ResultActions result = mockMvc.perform(
			post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(signUpUserRequest))
		);

		//then
		result.andExpect(status().isBadRequest());
	}

	@ParameterizedTest
	@MethodSource("validBody")
	void 사용자추가성공_validBody(String email, String password) throws Exception {
		//given
		SignUpUserRequest signUpUserRequest = SignUpUserRequest.builder()
			.email(email)
			.password(password)
			.build();

		//when
		ResultActions result = mockMvc.perform(
			post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(signUpUserRequest))
		);

		//then
		result.andExpect(status().isOk());
	}

	private static List<Arguments> invalidBody() {
		return List.of(
			Arguments.of("abc.com", "password"), //invalid email
			Arguments.of("", " "),  //blank test
			Arguments.of("abc@com", "test1!"), //invalid password
			Arguments.of("abc@gmail.com", "test232")
		);
	}

	private static List<Arguments> validBody() {
		return List.of(
			Arguments.of("test@abc.com", "password123!@#"),
			Arguments.of("abc@naver.com", "123Test!@#"),
			Arguments.of("abc@com", "!!!!123test")
		);
	}
}

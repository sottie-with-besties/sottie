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
import com.sottie.app.user.application.GetUserService;
import com.sottie.errors.GlobalExceptionHandler;

@ExtendWith(MockitoExtension.class)
public class GetUserControllerTest {

	@InjectMocks
	private GetUserController controller;

	@Mock
	private GetUserService service;

	private MockMvc mockMvc;

	private String url;

	@BeforeEach
	void init() {
		this.url = "/sottie/login/users";
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
			.setControllerAdvice(new GlobalExceptionHandler())
			.setValidator(new LocalValidatorFactoryBean())
			.build();
	}

	@ParameterizedTest
	@MethodSource("invalidBody")
	void 사용자조회실패_invalidBody(String email, String password) throws Exception {
		//given
		LoginUserRequest loginUserRequest = LoginUserRequest.builder()
			.email(email)
			.password(password)
			.build();

		//when
		ResultActions result = mockMvc.perform(
			post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(loginUserRequest))
		);

		//then
		result.andExpect(status().isBadRequest());
	}

	private static List<Arguments> invalidBody() {
		return List.of(
			Arguments.of("abc.com", "password"), //invalid email
			Arguments.of("", " "),  //blank test
			Arguments.of("abc@com", "") //invalid password
		);
	}
}

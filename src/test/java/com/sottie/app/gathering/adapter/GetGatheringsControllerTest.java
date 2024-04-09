package com.sottie.app.gathering.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sottie.app.gathering.application.AddGatheringService;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GenderCategory;
import com.sottie.errors.GlobalExceptionHandler;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GetGatheringsControllerTest {

	@InjectMocks
	private AddGatheringController controller;

	@Mock
	private AddGatheringService service;

	private MockMvc mockMvc;

	private String url;

	@BeforeEach
	void init() {
		url = "/sottie/gathering";
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
			.setControllerAdvice(new GlobalExceptionHandler())
			.setValidator(new LocalValidatorFactoryBean())
			.build();
	}

	@ParameterizedTest
	@MethodSource("invalidBody")
	void 모집추가실패_invalidBody(GatheringCategory gatheringCategory,
							String title,
							Long locationId,
							String contents,
							Integer peopleNum,
							Integer femaleNum,
							Integer maleNum,
							Integer ageTo,
							Integer ageFrom,
							Integer mannerTemperature,
							GenderCategory genderRestriction,
							Boolean mannerRestrictionYn,
							Boolean ageRestrictionYn) throws Exception {
		//given
		DefaultGatheringRequest defaultUserRequest = DefaultGatheringRequest.builder()
				.gatheringCategory(gatheringCategory)
				.title(title)
				.locationId(locationId)
				.contents(contents)
				.peopleNum(peopleNum)
				.femaleNum(femaleNum)
				.maleNum(maleNum)
				.ageTo(ageTo)
				.ageFrom(ageFrom)
				.mannerTemperature(mannerTemperature)
				.genderRestriction(genderRestriction)
				.ageRestrictionYn(ageRestrictionYn)
				.mannerRestrictionYn(mannerRestrictionYn)
				.build();

		//when
		ResultActions result = mockMvc.perform(
			post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(defaultUserRequest))
		);

		//then
		result.andExpect(status().isBadRequest());
	}

	@ParameterizedTest
	@MethodSource("validBody")
	void 사용자추가성공_validBody(GatheringCategory gatheringCategory,
						   String title,
						   Long locationId,
						   String contents,
						   Integer peopleNum,
						   Integer femaleNum,
						   Integer maleNum,
						   Integer ageTo,
						   Integer ageFrom,
						   Integer mannerTemperature,
						   GenderCategory genderRestriction,
						   Boolean mannerRestrictionYn,
						   Boolean ageRestrictionYn) throws Exception {

		//given
		DefaultGatheringRequest defaultUserRequest = DefaultGatheringRequest.builder()
				.gatheringCategory(gatheringCategory)
				.title(title)
				.locationId(locationId)
				.contents(contents)
				.peopleNum(peopleNum)
				.femaleNum(femaleNum)
				.maleNum(maleNum)
				.ageTo(ageTo)
				.ageFrom(ageFrom)
				.mannerTemperature(mannerTemperature)
				.genderRestriction(genderRestriction)
				.ageRestrictionYn(ageRestrictionYn)
				.mannerRestrictionYn(mannerRestrictionYn)
				.build();

		//when
		ResultActions result = mockMvc.perform(
			post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(defaultUserRequest))
		);

		//then
		result.andExpect(status().isCreated());
	}

	private static List<Arguments> invalidBody() {
//		.gatheringCategory(gatheringCategory)
//		.title(title)
//		.locationId(locationId)
//		.contents(contents)
//		.peopleNum(peopleNum)
//		.femaleNum(femaleNum)
//		.maleNum(maleNum)
//		.ageTo(ageTo)
//		.ageFrom(ageFrom)
//		.mannerTemperature(mannerTemperature)
//		.genderRestriction(genderRestriction)
//		.ageRestrictionYn(ageRestrictionYn)
//		.mannerRestrictionYn(mannerRestrictionYn)
		return List.of(
			Arguments.of(GatheringCategory.FRIENDSHIP, "chimactime!", 10L, "xxx xxx xxx", 6, 3, 3, 25, 30, 37, GenderCategory.NONE, true, true) //invalid email
		);
	}

	private static List<Arguments> validBody() {
		return List.of(
			Arguments.of(GatheringCategory.FRIENDSHIP, "chimactime!", 10L, "xxx xxx xxx", 6, 3, 3, 25, 30, 37, GenderCategory.NONE, true, true) //invalid email
		);
	}
}

package com.sottie.app.user.application;

import static com.sottie.app.user.model.UserTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;

@ExtendWith(MockitoExtension.class)
class GetUserServiceTest {

	@InjectMocks
	@Spy
	private GetUserService getUserService;

	@Mock
	private UserRepository userRepository;

	@Test
	void 로그인사용자_조회_성공() {
		//given
		doReturn(Optional.of(user()))
			.when(userRepository)
			.findByEmail("email");

		doReturn(true)
			.when(getUserService)
			.isMatched("test123!!", "test123!!");

		//when
		User result = getUserService.getUserForLogin("email", "test123!!");

		//then
		assertThat(result).isNotNull();

	}

	@Test
	void 로그인사용자_조회_실패_존재하지않음() {
		//given
		doReturn(Optional.empty())
			.when(userRepository)
			.findByEmail("email");

		//when
		//then
		assertThatThrownBy(() -> getUserService.getUserForLogin("email", "password"))
			.isInstanceOf(CommonException.class);
	}

	@Test
	void 로그인사용자_조회_실패_비밀번호불일치() {
		//given
		doReturn(Optional.of(user()))
			.when(userRepository)
			.findByEmail("email");

		doReturn(false)
			.when(getUserService)
			.isMatched("test123!!", "test123!!");

		//when
		assertThatThrownBy(() -> getUserService.getUserForLogin("email", "test123!!"))
			.isInstanceOf(CommonException.class);

	}

	@Test
	void 사용자_조회_byEmail_성공() {
		//given
		doReturn(Optional.of(user()))
			.when(userRepository)
			.findByEmail("email");
		//when
		User result = getUserService.getUserByEmail("email");

		//then
		assertThat(result).isNotNull();

	}

	@Test
	void 사용자_조회_byEmail_실패() {
		//given
		doReturn(Optional.empty())
			.when(userRepository)
			.findByEmail("email");
		//when
		//then
		assertThatThrownBy(() -> getUserService.getUserByEmail("email"))
			.isInstanceOf(CommonException.class);

	}

	@Test
	void 사용자_조회_byPhone_성공() {
		//given
		doReturn(Optional.of(user()))
			.when(userRepository)
			.findByPhoneNumber("phone");
		//when
		User result = getUserService.getUserByPhoneNumber("phone");

		//then
		assertThat(result).isNotNull();

	}

	@Test
	void 사용자_조회_byPhone_실패() {
		//given
		doReturn(Optional.empty())
			.when(userRepository)
			.findByPhoneNumber("phone");
		//when
		//then
		assertThatThrownBy(() -> getUserService.getUserByPhoneNumber("phone"))
			.isInstanceOf(CommonException.class);

	}

	@Test
	void 사용자_존재여부조회_byEmail_성공() {
		//given
		doReturn(true)
			.when(userRepository)
			.existsByEmail("email");
		//when
		boolean result = getUserService.isExistingUserByEmail("email");

		//then
		assertThat(result).isTrue();

	}

}

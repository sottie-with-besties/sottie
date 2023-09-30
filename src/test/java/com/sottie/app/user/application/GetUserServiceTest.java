package com.sottie.app.user.application;

import static com.sottie.app.user.model.UserTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;

@ExtendWith(MockitoExtension.class)
public class GetUserServiceTest {

	@InjectMocks
	private GetUserService getUserService;

	@Mock
	private UserRepository userRepository;

	@Test
	void 사용자조회_성공() {
		//given
		doReturn(Optional.of(user()))
			.when(userRepository)
			.findByEmailAndPassword("email", "password");
		//when
		User result = getUserService.getUserForLogin("email", "password");

		//then
		assertThat(result).isNotNull();

	}

	@Test
	void 사용자조회_실패() {
		//when
		doReturn(Optional.empty())
			.when(userRepository)
			.findByEmailAndPassword("email", "password");

		//then
		assertThatThrownBy(() -> getUserService.getUserForLogin("email", "password"))
			.isInstanceOf(CommonException.class);
	}
}

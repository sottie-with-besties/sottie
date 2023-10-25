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

import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;

@ExtendWith(MockitoExtension.class)
class UpdateUserServiceTest {

	@InjectMocks
	@Spy
	private UpdateUserService service;

	@Mock
	private UserRepository userRepository;

	@Test
	void 비밀번호리셋_성공() {
		doReturn(Optional.of(user()))
			.when(userRepository)
			.findByEmail("email");

		service.resetPassword("email", "password");

		verify(service, times(1)).encrypt("password");
	}

	@Test
	void 비밀번호리셋_실패_사용자존재하지않음() {
		doReturn(Optional.empty())
			.when(userRepository)
			.findByEmail("email");

		assertThatThrownBy(() -> service.resetPassword("email", "password"))
			.isInstanceOf(CommonException.class);
	}
}

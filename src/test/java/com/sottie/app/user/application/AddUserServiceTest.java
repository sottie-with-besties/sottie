package com.sottie.app.user.application;

import static com.sottie.app.user.model.UserTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;

@ExtendWith(MockitoExtension.class)
public class AddUserServiceTest {

	@InjectMocks
	private AddUserService addUserService;

	@Mock
	private UserRepository userRepository;

	@Test
	void 사용자추가_성공() {
		//given
		doReturn(false)
			.when(userRepository)
			.existsByEmail("test@gmail.com");
		//when
		addUserService.addUserForSignUp(user());

		//then
		verify(userRepository, times(1)).save(any());

	}

	@Test
	void 사용자추기_실패() {
		//when
		doReturn(true)
			.when(userRepository)
			.existsByEmail("test@gmail.com");

		//then
		assertThatThrownBy(() -> addUserService.addUserForSignUp(user()))
			.isInstanceOf(CommonException.class);
	}

}

package com.sottie.app.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.user.error.UserErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetUserService {

	private final UserRepository userRepository;

	public User getUserForLogin(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password)
			.orElseThrow(() -> CommonException.builder(UserErrorCode.USER_UNAUTHORIZED).build());
	}
}

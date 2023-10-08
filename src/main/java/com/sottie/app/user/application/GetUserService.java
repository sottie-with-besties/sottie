package com.sottie.app.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.user.error.UserErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonErrorCode;
import com.sottie.errors.CommonException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetUserService implements Encryptor {

	private final UserRepository userRepository;

	public User getUserForLogin(String email, String password) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
		if (isMatched(password, user.getPassword())) {
			return user;
		} else {
			throw CommonException.builder(UserErrorCode.USER_UNAUTHORIZED).build();
		}
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_ALREADY_EXISTS).build());
	}

	public User getUserByPhoneNumber(String phoneNumber) {
		return userRepository.findByPhoneNumber(phoneNumber)
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_ALREADY_EXISTS).build());
	}

	public boolean isExistingUserByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
}

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
@Transactional
public class AddUserService {

	private final UserRepository userRepository;

	public User addUserForSignUp(User newUser) {
		if (userRepository.existsByEmail(newUser.getEmail())) {
			throw CommonException.builder(UserErrorCode.USER_ALREADY_EXISTS).build();
		}
		return userRepository.save(newUser);
	}
}

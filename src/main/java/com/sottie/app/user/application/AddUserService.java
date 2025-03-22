package com.sottie.app.user.application;

import com.sottie.app.cash.model.Cash;
import com.sottie.app.cash.repository.CashRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.user.error.UserErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import com.sottie.utils.Encryptor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AddUserService implements Encryptor {

	private final UserRepository userRepository;

	private final CashRepository cashRepository;

	public User addUserForSignUp(User newUser) {

		if (userRepository.existsByEmail(newUser.getEmail())) {
			throw CommonException.builder(UserErrorCode.USER_ALREADY_EXISTS).build();
		}

		if (userRepository.existsByPhoneNumber(newUser.getPhoneNumber())) {
			throw CommonException.builder(UserErrorCode.USER_ALREADY_EXISTS).build();
		}

		newUser.setPassword(encrypt(newUser.getPassword()));

		User savedUser = userRepository.save(newUser);

		Cash newCashAccount = Cash.createNewCashAccount(savedUser.getId());
		cashRepository.save(newCashAccount);

		return savedUser;

	}
}

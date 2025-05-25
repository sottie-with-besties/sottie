package com.sottie.app.user.application;

import com.sottie.authentication.JwtProvider;
import com.sottie.authentication.SottieAuthenticationManager;
import com.sottie.authentication.SottieAuthenticationRequestToken;
import com.sottie.utils.SottieUserUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.user.error.UserErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonErrorCode;
import com.sottie.errors.CommonException;
import com.sottie.utils.Encryptor;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetUserService implements Encryptor {

	private final UserRepository userRepository;
	private final SottieAuthenticationManager sottieAuthenticationManager;

	public User getUserForLogin(String email, String password) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
		if (isMatched(password, user.getPassword())) {
			user.setPassword(null);
			// 유저 키값 필요
			SottieAuthenticationRequestToken authenticationRequestToken = SottieAuthenticationRequestToken.builder()
					.userName(user.getId().toString())
					.build();
			log.info("sottieAuthentication.getName() ::: {}", authenticationRequestToken.getUserName());
			sottieAuthenticationManager.authenticate(authenticationRequestToken);

			return user;
		} else {
			throw CommonException.builder(UserErrorCode.USER_UNAUTHORIZED).build();
		}
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
	}

	public User getUserByPhoneNumber(String phoneNumber) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {

			return userRepository.findByPhoneNumberAndPrivateMode(phoneNumber, false)
					.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());

		} else {
			throw CommonException.builder(UserErrorCode.CANNOT_FIND_USER).build();
		}

	}

	public Boolean isExistingUserByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
	}

	public Boolean isExistingNickName(String nickName) {
		return userRepository.existsByNickName(nickName);
	}
}

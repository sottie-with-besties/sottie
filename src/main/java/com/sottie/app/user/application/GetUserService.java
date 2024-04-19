package com.sottie.app.user.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.user.error.UserErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonErrorCode;
import com.sottie.errors.CommonException;
import com.sottie.utils.Encryptor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetUserService implements Encryptor {

	private final HttpServletRequest httpServletRequest;
	private final UserRepository userRepository;

	public User getUserForLogin(String email, String password) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
		if (isMatched(password, user.getPassword())) {
			// 기존 session 파기
			httpServletRequest.getSession().invalidate();

			// session 이 있으면 가져오고 없으면 session 을 생성해서 return (default = true)
			HttpSession session = httpServletRequest.getSession(true);
			session.setAttribute("userId", user.getId());
			session.setMaxInactiveInterval(1800);

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
		return userRepository.findByPhoneNumber(phoneNumber)
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
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

package com.sottie.app.user.application;

import com.sottie.authentication.JwtProvider;
import com.sottie.authentication.SottieAuthentication;
import com.sottie.authentication.SottieAuthenticationManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.user.error.UserErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonErrorCode;
import com.sottie.errors.CommonException;
import com.sottie.utils.Encryptor;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetUserService implements Encryptor {

	private final HttpServletRequest httpServletRequest;
	private final UserRepository userRepository;
	private final SottieAuthenticationManager sottieAuthenticationManager;
	private final JwtProvider jwtProvider;

	public User getUserForLogin(String email, String password) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
		if (isMatched(password, user.getPassword())) {



			// 유저 키값 필요
			jwtProvider.generate(user.getId(), "USER");
			SottieAuthentication sottieAuthentication = new SottieAuthentication();
			sottieAuthentication.addAuthorities(() -> "ROLE_USER");
			sottieAuthenticationManager.authenticate(sottieAuthentication);
			// 기존 session 파기
//			httpServletRequest.getSession().invalidate();
//
//			// session 이 있으면 가져오고 없으면 session 을 생성해서 return (default = true)
//			HttpSession session = httpServletRequest.getSession(true);
//			session.setAttribute("userId", user.getId());
//			session.setMaxInactiveInterval(1800);


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

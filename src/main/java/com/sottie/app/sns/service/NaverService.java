package com.sottie.app.sns.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sottie.app.sns.dto.NaverProfile;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NaverService {

	private final UserRepository userRepository;

	public void loginProcess(NaverProfile naverProfile) {

		// User Table 에서 snsType, snsId 로 기존 회원 여부 확인
		Optional<User> user = userRepository.findByIdentifier(naverProfile.getId());

		// 존재하지 않으면 회원가입
		if (!user.isPresent()) {
			User userInfo = User.builder()
				.name(naverProfile.getName())
				.email(naverProfile.getEmail())
				.nickName(naverProfile.getNickname())
				.phoneNumber(naverProfile.getMobile())
				.gender(naverProfile.getGender())
				.identifier(naverProfile.getId())
				.birthYear(naverProfile.getBirthyear())
				// .birthYear(naverProfile.getBirthyear() + StringUtils.remove(naverProfile.getBirthday(), "-"))
				.build();

			userRepository.save(userInfo);
		}

		// 로그인 method 호출
		// login();
	}
}

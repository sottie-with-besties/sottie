package com.sottie.app.profile.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.profile.model.Profile;
import com.sottie.app.profile.repository.ProfileRepository;
import com.sottie.app.user.application.GetUserService;
import com.sottie.errors.CommonErrorCode;
import com.sottie.errors.CommonException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class GetProfileService {

	private final ProfileRepository profileRepository;
	private final GetUserService getUserService;

	public Profile getProfileByUser(Long userId) {
		return profileRepository.findByUserId(userId)
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
	}

	//프로필 데이터 검색 시, 보여지는 사용자 명은 어떻게 할지 확인 필요
	public Profile getProfileByPhone(String phoneNumber) {
		return profileRepository.findByUserId(getUserService.getUserByPhoneNumber(phoneNumber).getId())
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
	}
}

package com.sottie.app.profile.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.profile.model.Profile;
import com.sottie.app.profile.repository.ProfileRepository;
import com.sottie.errors.CommonErrorCode;
import com.sottie.errors.CommonException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class GetProfileService {

	private final ProfileRepository repository;

	public Profile getProfileByUser(Long userId) {
		return repository.findByUserId(userId)
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
	}
}

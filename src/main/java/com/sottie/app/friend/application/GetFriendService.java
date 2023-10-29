package com.sottie.app.friend.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.friend.model.FriendProfile;
import com.sottie.app.friend.repository.FriendRepository;
import com.sottie.app.profile.application.GetProfileService;
import com.sottie.app.profile.model.Profile;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetFriendService {

	private final FriendRepository repository;
	private final GetProfileService profileService;

	//Todo @juyoung
	//tc추가하기
	//프로필 정렬 (이름순), pageable하게 변경할 건지 검토 필요

	public List<FriendProfile> getFriendProfileList(Long userId) {
		List<FriendProfile> profileList = new ArrayList<>();
		repository.findByUserIdAndBlocked(userId, false).stream()
			.forEach(
				friend -> {
					Profile profile = profileService.getProfileByUser(friend.getFriendId());
					profileList.add(FriendProfile.builder()
						.userId(friend.getFriendId())
						.alias(friend.getAlias())
						.photo(profile.getPhoto())
						.moodStatus(profile.getMoodStatus())
						.introPhrase(profile.getIntroPhrase()).build());
				}
			);

		return profileList;
	}
}

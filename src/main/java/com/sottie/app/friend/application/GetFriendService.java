package com.sottie.app.friend.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import com.sottie.utils.SottieUserUtils;
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

	private final UserRepository userRepository;
	private final FriendRepository friendRepository;
	private final GetProfileService profileService;

	//Todo @juyoung
	//tc추가하기
	//프로필 정렬 (이름순), pageable하게 변경할 건지 검토 필요

	public List<FriendProfile> getFriendProfileList() {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {

			User user = optUser.get();

			List<FriendProfile> profileList = new ArrayList<>();
			friendRepository.findByUserIdAndBlocked(user.getId(), false)
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

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}


	}

	public List<FriendProfile> searchFriendProfileListByAlias(String alias) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {

			User user = optUser.get();

			List<FriendProfile> profileList = new ArrayList<>();
			friendRepository.findByUserIdAndAliasAndBlocked(user.getId(), alias, false).stream()
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

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}

	}
}

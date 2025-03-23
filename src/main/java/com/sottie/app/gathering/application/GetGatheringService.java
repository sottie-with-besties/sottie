package com.sottie.app.gathering.application;

import com.sottie.app.friend.application.GetFriendService;
import com.sottie.app.friend.model.Friend;
import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.gathering.model.GenderCategory;
import com.sottie.app.gathering.model.dto.GatheringDto;
import com.sottie.app.gathering.model.record.GetGatheringRequest;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.gathering.repository.GatheringUserRepository;
import com.sottie.app.gathering.specification.GatheringSpecification;
import com.sottie.app.user.error.UserErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import com.sottie.utils.SottieUserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetGatheringService {

	private final GatheringRepository gatheringRepository;
	private final GatheringUserRepository gatheringUserRepository;
	private final UserRepository userRepository;

	private final GetFriendService getFriendService;


	public List<GatheringDto> getGatherings(GetGatheringRequest getGatheringRequest) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {

			User user = optUser.get();

			List<Friend> blockedFriends = getFriendService.getBlockedFriends(user.getId());

			List<GatheringDto> gatheringDtos = new ArrayList<>();

			if (getGatheringRequest == null) {

				List<Gathering> gatherings = gatheringRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedDate"));

				List<Gathering> blockFilteredGatherings = getBlockFilteredGatherings(gatherings, blockedFriends);

				for (Gathering blockFilteredGathering : blockFilteredGatherings) {
					gatheringDtos.add(GatheringDto.from(blockFilteredGathering));
				}

			} else {

				GatheringCategory gatheringCategory = getGatheringRequest.gatheringCategory();
				String title = getGatheringRequest.title();
				Long locationId = getGatheringRequest.locationId();
				LocalDateTime searchStartTime = getGatheringRequest.searchStartDate();
				LocalDateTime searchEndTime = getGatheringRequest.searchEndDate();
				Integer peopleNum = getGatheringRequest.peopleNum();
				GenderCategory genderRestriction = getGatheringRequest.genderRestriction();
				Boolean mannerRestrictionYn = getGatheringRequest.mannerRestriction();
				Boolean ageRestrictionYn = getGatheringRequest.ageRestriction();

				Specification<Gathering> spec = Specification.where(GatheringSpecification.equalGatheringCategory(gatheringCategory));
				spec = spec.or(GatheringSpecification.likeTitle(title)
						.or(GatheringSpecification.equalLocationId(locationId))
						.or(GatheringSpecification.equalPeopleNum(peopleNum))
						.or(GatheringSpecification.restrictGender(genderRestriction))
						.or(GatheringSpecification.restrictManner(mannerRestrictionYn))
						.or(GatheringSpecification.restrictAge(ageRestrictionYn))
						.or(GatheringSpecification.betweenGatheringDate(searchStartTime, searchEndTime))
				);

				List<Gathering> gatherings = gatheringRepository.findAll(spec);

				List<Gathering> blockFilteredGatherings = getBlockFilteredGatherings(gatherings, blockedFriends);

				for (Gathering blockFilteredGathering : blockFilteredGatherings) {
					gatheringDtos.add(GatheringDto.from(blockFilteredGathering));
				}

			}
			return gatheringDtos;

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}

	}

	/**
	 * 현재 join 한 gathering 목록을 가져옴
	 * @param userId
	 * @return List<GatheringDto>
	 */
	public List<GatheringDto> getJoinedGatherings(Long userId) {

		Optional<User> userOpt = userRepository.findById(userId);

		if (userOpt.isPresent()) {

			List<GatheringUser> gatheringUsers = gatheringUserRepository.findByUserId(userOpt.get().getId());

			List<GatheringDto> gatheringDtos = new ArrayList<>();
			for (GatheringUser gatheringUser : gatheringUsers) {
				Optional<Gathering> optGathering = gatheringRepository.findById(gatheringUser.getGatheringId());
				optGathering.ifPresent(gathering -> gatheringDtos.add(GatheringDto.from(gathering)));
			}

			return gatheringDtos;

		} else {
			throw CommonException.builder(UserErrorCode.USER_ALREADY_EXISTS).build();
		}
	}

	// TODO 로직 개선 필요 다중 iter
	private List<Gathering> getBlockFilteredGatherings(List<Gathering> gatherings, List<Friend> blockedFriends) {

		for (Gathering gathering : gatherings) {

			List<Long> friendIds = blockedFriends.stream().map(Friend::getFriendId).toList();

			for (Long friendId : friendIds) {
				if (gathering.getHost().equals(friendId)) {
					gatherings.remove(gathering);
				}

				List<GatheringUser> gatheringUsers = gatheringUserRepository.findByUserId(friendId);
				for (GatheringUser gatheringUser : gatheringUsers) {
					gatherings.removeIf(g -> g.getId().equals(gatheringUser.getGatheringId()));
				}
			}
		}

		return gatherings;
	}

}

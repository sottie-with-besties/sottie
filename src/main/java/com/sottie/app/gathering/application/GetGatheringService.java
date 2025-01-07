package com.sottie.app.gathering.application;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetGatheringService {

	private final GatheringRepository gatheringRepository;
	private final GatheringUserRepository gatheringUserRepository;
	private final UserRepository userRepository;


	public List<GatheringDto> getGatherings(GetGatheringRequest getGatheringRequest) {

		List<GatheringDto> gatheringDtos = new ArrayList<>();

		if (getGatheringRequest == null) {

			List<Gathering> gatherings = gatheringRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedDate"));
			for (Gathering gathering : gatherings) {
				gatheringDtos.add(GatheringDto.from(gathering));
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
			for (Gathering gathering : gatherings) {
				gatheringDtos.add(GatheringDto.from(gathering));
			}

		}
		return gatheringDtos;
	}

	public List<GatheringDto> getJoinedGatherings(Long userId) {
		Optional<User> userOpt = userRepository.findById(userId);
		if (userOpt.isPresent()) {
			List<GatheringUser> gatheringUsers = userOpt.get().getGatheringUsers();

			List<GatheringDto> gatheringDtos = new ArrayList<>();
			for (GatheringUser gatheringUser : gatheringUsers) {
				gatheringDtos.add(gatheringUser.toGatheringDto());
			}

			// TODO 오우.. gathering <> gatheringUser <> User 관계 다시 정립해야할듯. 데이터크기 박살..!
			return gatheringDtos;
		} else {
			throw CommonException.builder(UserErrorCode.USER_ALREADY_EXISTS).build();
		}
	}

}

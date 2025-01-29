package com.sottie.app.gathering.application;

import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.gathering.model.dto.GatheringDto;
import com.sottie.app.gathering.model.record.CreateGatheringRequest;
import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GenderCategory;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.gathering.repository.GatheringUserRepository;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import com.sottie.utils.SottieUserUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreateGatheringService {

	private final GatheringRepository gatheringRepository;
	private final UserRepository userRepository;
	private final GatheringUserRepository gatheringUserRepository;

	private static final int GATHERING_TIME_LIMIT = 168;

	public GatheringDto createGathering(CreateGatheringRequest createGatheringRequest) {

		Integer userId = SottieUserUtils.getUserIdInt();

		Optional<User> optUser = userRepository.findById(userId.longValue());

		if (optUser.isPresent()) {
			User user = optUser.get();

			Gathering gathering = createGatheringRequest.to(user.getId());

			checkCreateGatheringValidation(gathering);

			// peopleNum 증가
			// user 가 female 일 경우 femaleNum 증가
			// user 가 male 일 경우 maleNum 증가
			gathering.plusPeopleNum(user);

			Gathering savedGathering = gatheringRepository.save(gathering);

			GatheringUser gatheringUser = GatheringUser.mappingGatheringUser(user.getId(), savedGathering.getId());
			gatheringUserRepository.save(gatheringUser);

			return GatheringDto.from(gathering);

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}

	private void checkCreateGatheringValidation(Gathering gathering) {

		// 필수 값 Validation Check
		checkNotNullValidation(gathering);

		// 성별 제한 Valid Check
		checkGenderRestriction(gathering);

		// 나이 제한 Valid Check
		checkGatheringAgeRestriction(gathering);

		// 약속 시간 제한 Valid Check
		checkGatheringDate(gathering);

	}

	private void checkNotNullValidation(Gathering gathering) {
		if (gathering.getTitle().isEmpty()) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getLocationId() == null || gathering.getLocationId().equals(0L)) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getPeopleNum() == null || gathering.getPeopleNum().equals(0)) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		}
	}

	private void checkGenderRestriction(Gathering gathering) {

		if (Objects.isNull(gathering.getGatheringCategory())) {
			log.error(GatheringErrorCode.INVALID_GENDER_RESTRICTION.getMessage());
			throw CommonException.builder(GatheringErrorCode.INVALID_GENDER_RESTRICTION).build();
		}

		if (gathering.getGenderRestriction().equals(GenderCategory.MIX)) {
			if (gathering.getMaleNum().equals(0) ||  gathering.getFemaleNum().equals(0)) {
				log.error(GatheringErrorCode.INVALID_GENDER_RESTRICTION.getMessage());
				throw CommonException.builder(GatheringErrorCode.INVALID_GENDER_RESTRICTION).build();
			}

		} else if (gathering.getGenderRestriction().equals(GenderCategory.MALE)) {
			if ((!Objects.equals(gathering.getMaleNum(), gathering.getPeopleNum()))) {
				log.error(GatheringErrorCode.INVALID_GENDER_RESTRICTION.getMessage());
				throw CommonException.builder(GatheringErrorCode.INVALID_GENDER_RESTRICTION).build();
			}

		} else if (gathering.getGenderRestriction().equals(GenderCategory.FEMALE)) {
			if ((!Objects.equals(gathering.getFemaleNum(), gathering.getPeopleNum()))) {
				log.error(GatheringErrorCode.INVALID_GENDER_RESTRICTION.getMessage());
				throw CommonException.builder(GatheringErrorCode.INVALID_GENDER_RESTRICTION).build();
			}

		} else if (gathering.getGenderRestriction().equals(GenderCategory.NONE)) {
			if (!(gathering.getMaleNum().equals(0) && gathering.getFemaleNum().equals(0))) {
				log.error(GatheringErrorCode.INVALID_GENDER_RESTRICTION.getMessage());
				throw CommonException.builder(GatheringErrorCode.INVALID_GENDER_RESTRICTION).build();
			}
		}
	}

	// 현 시각부터 7일 이내의 시간을 설정하게끔 강제 (프론트에서 비활성화 우선)
	private void checkGatheringDate(Gathering gathering) {

		Duration between = Duration.between(gathering.getGatheringDate(), LocalDateTime.now());

		if (between.toHours() > GATHERING_TIME_LIMIT) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
		}
	}

	private void checkGatheringAgeRestriction(Gathering gathering) {

		if (gathering.getAgeRestriction().equals(Boolean.TRUE)) {
			if (gathering.getAgeTo().equals(0) || gathering.getAgeFrom().equals(0)) {
				log.error(GatheringErrorCode.INVALID_AGE_RESTRICTION.getMessage());
				throw CommonException.builder(GatheringErrorCode.INVALID_AGE_RESTRICTION).build();
			}

		} else if (gathering.getAgeRestriction().equals(Boolean.FALSE)) {
			if (!gathering.getAgeTo().equals(0) || !gathering.getAgeFrom().equals(0)) {
				log.error(GatheringErrorCode.INVALID_AGE_RESTRICTION.getMessage());
				throw CommonException.builder(GatheringErrorCode.INVALID_AGE_RESTRICTION).build();
			}
		}
	}
}

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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

	private final HttpServletRequest httpServletRequest;
	private final GatheringRepository gatheringRepository;
	private final UserRepository userRepository;

	private final GatheringUserRepository gatheringUserRepository;

	private static final long GATHERING_TIME_LIMIT = 168L;

	public GatheringDto createGathering(CreateGatheringRequest createGatheringRequest) {

		// TODO @연식 님 Token 방식 적용 필요 부분
		// user session
//		HttpSession session = httpServletRequest.getSession(false);
//		Long loginUserId = null;
//		if (session != null) {
//			loginUserId = (Long) session.getAttribute("userId");
//		} else {
//			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
//		}

		// TODO 테스트 위해서 user 고정
//		Optional<User> optUser = userRepository.findById(loginUserId);
		Optional<User> optUser = userRepository.findById(17L);

		if (optUser.isPresent()) {
			User user = optUser.get();

			Gathering gathering = createGatheringRequest.to(user.getId());

			checkCreateGatheringValidation(gathering);
			checkGatheringDateValidation(createGatheringRequest.gatheringDate());

			// peopleNum 증가
			// user 가 female 일 경우 femaleNum 증가
			// user 가 male 일 경우 maleNum 증가
			gathering.plusPeopleNum(user);

			Gathering saved = gatheringRepository.save(gathering);

			GatheringUser gatheringUser = GatheringUser.mappingGatheringUser(user, saved);
			gatheringUserRepository.save(gatheringUser);

			return GatheringDto.from(gathering);

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}

	private void checkCreateGatheringValidation(Gathering gathering) {

		checkGenderRestriction(gathering);

		if (gathering.getGatheringCategory() == null) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getTitle().isEmpty()) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getLocationId() == null || gathering.getLocationId().equals(0L)) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getPeopleNum() == null || gathering.getPeopleNum().equals(0)) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (!gathering.getPeopleNum().equals(gathering.getFemaleNum() + gathering.getMaleNum())) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getGenderRestriction().equals(GenderCategory.FEMALE)) {
			if ((!Objects.equals(gathering.getFemaleNum(), gathering.getPeopleNum()))) {
				log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}

		} else if (gathering.getGenderRestriction().equals(GenderCategory.MALE)) {
			if ((!Objects.equals(gathering.getMaleNum(), gathering.getPeopleNum()))) {
				log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}

		}else if (gathering.getAgeRestriction().equals(Boolean.TRUE)) {
			if (gathering.getAgeTo().equals(0) && gathering.getAgeFrom().equals(0)) {
				log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}
		}
	}

	private void checkGenderRestriction(Gathering gathering) {
		if (gathering.getMaleNum() > 0 && gathering.getFemaleNum() > 0) {
			if (!gathering.getGenderRestriction().equals(GenderCategory.MIX)) {
				log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}
		}
	}

	// 현 시각부터 7일 이내의 시간을 설정하게끔 강제 (프론트에서 비활성화 우선)
	private boolean checkGatheringDateValidation(LocalDateTime gatheringDate) {

		Duration between = Duration.between(gatheringDate, LocalDateTime.now());

		if (between.toHours() > GATHERING_TIME_LIMIT) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
		}
		return true;
	}
}

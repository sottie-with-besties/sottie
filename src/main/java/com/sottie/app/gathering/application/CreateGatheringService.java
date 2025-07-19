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

	// TODO 캐시/포인트 사용 로직 들어가야함
	public GatheringDto createGathering(CreateGatheringRequest createGatheringRequest) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

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
		CommonGatheringService.checkNotNullValidation(gathering);

		// 성별 제한 Valid Check
		CommonGatheringService.checkGenderRestriction(gathering);

		// 나이 제한 Valid Check
		CommonGatheringService.checkGatheringAgeRestriction(gathering);

		// 약속 시간 제한 Valid Check
		CommonGatheringService.checkGatheringDate(gathering);

	}
}

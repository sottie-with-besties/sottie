package com.sottie.app.gathering.application;

import com.sottie.app.gathering.model.dto.GatheringDto;
import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.gathering.model.record.JoinGatheringRequest;
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

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JoinGatheringService {

	private final HttpServletRequest httpServletRequest;
	private final UserRepository userRepository;
	private final GatheringRepository gatheringRepository;
	private final GatheringUserRepository gatheringUserRepository;

	// TODO 캐시/포인트 사용 로직 들어가야함
	public GatheringDto joinGathering(JoinGatheringRequest joinGatheringRequest) {

		Integer userId = SottieUserUtils.getUserIdInt();

		Optional<User> optUser = userRepository.findById(userId.longValue());

		if (optUser.isPresent()) {
			Optional<Gathering> optGathering = gatheringRepository.findById(joinGatheringRequest.gatheringId());

			if (optGathering.isPresent()) {
				Gathering gathering = optGathering.get();
				User user = optUser.get();

				// 참여를 표시한 사용자 리스트에 추가 (맵핑 테이블에 데이터 추가)
				Optional<GatheringUser> optGatheringUser = gatheringUserRepository.findByGatheringIdAndUserId(gathering.getId(), user.getId());
				if (optGatheringUser.isEmpty()) {
					GatheringUser gatheringUser = GatheringUser.mappingGatheringUser(user.getId(), gathering.getId());
					gatheringUserRepository.save(gatheringUser);

					// peopleNum 증가
					// user 가 female 일 경우 femaleNum 증가
					// user 가 male 일 경우 maleNum 증가
					gathering.plusPeopleNum(user);

					gatheringRepository.save(gathering);

					return GatheringDto.from(gathering);

				} else {
					log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
					throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
				}

			} else {
				log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}

		} else {
			log.error(GatheringErrorCode.NOT_HOST_USER.getMessage());
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}

}

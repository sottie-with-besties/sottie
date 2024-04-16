package com.sottie.app.gathering.application;

import com.sottie.app.gathering.adapter.DefaultGatheringRequest;
import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.gathering.repository.GatheringUserRepository;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class JoinGatheringService {

	private final HttpServletRequest httpServletRequest;
	private final UserRepository userRepository;
	private final GatheringRepository gatheringRepository;
	private final GatheringUserRepository gatheringUserRepository;

	public Gathering joinGathering(DefaultGatheringRequest defaultGatheringRequest) {



		// user session
		HttpSession session = httpServletRequest.getSession(false);
		Long loginUserId = null;
		if (session != null) {
			loginUserId = (Long) session.getAttribute("userId");
		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
		Optional<User> optUser = userRepository.findById(loginUserId);


		if (optUser.isPresent()) {
			Optional<Gathering> optGathering = gatheringRepository.findById(defaultGatheringRequest.id());

			if (optGathering.isPresent()) {
				Gathering gathering = optGathering.get();
				User user = optUser.get();

				// peopleNum 감소
				// user 가 female 일 경우 femaleNum 감소
				// user 가 male 일 경우 maleNum 감소
				gathering.plusPeopleNum(user);

				// 참여를 표시한 사용자 리스트에 추가 (맵핑 테이블에 데이터 추가)
				GatheringUser gatheringUser = GatheringUser.mappingGatheringUser(user, gathering);
				gatheringUserRepository.save(gatheringUser);

				return gatheringRepository.save(gathering);

			} else {
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}

}

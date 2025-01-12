package com.sottie.app.gathering.application;

import com.sottie.app.gathering.model.dto.GatheringDto;
import com.sottie.app.gathering.model.record.DefaultGatheringRequest;
import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.gathering.model.record.ExitGatheringRequest;
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
public class ExitGatheringService {
	private final HttpServletRequest httpServletRequest;
	private final UserRepository userRepository;
	private final GatheringRepository gatheringRepository;
	private final GatheringUserRepository gatheringUserRepository;

	public GatheringDto exitGathering(ExitGatheringRequest exitGatheringRequest) {

		// TODO @연식 님 Token 방식 적용 필요 부분
		// user session
//		HttpSession session = httpServletRequest.getSession(false);
//		Long loginUserId = null;
//		if (session != null) {
//			loginUserId = (Long) session.getAttribute("userId");
//		} else {
//			throw CommonException.builder(UserErrorCode.USER_UNAUTHORIZED).build();
//		}

		// TODO 테스트 위해서 user 고정
//		Optional<User> optUser = userRepository.findById(loginUserId);
		Optional<User> optUser = userRepository.findById(7L); // FEMALE TEST

		if (optUser.isPresent()) {
			Optional<Gathering> optGathering = gatheringRepository.findById(exitGatheringRequest.gatheringId());

			if (optGathering.isPresent()) {
				Gathering gathering = optGathering.get();
				User user = optUser.get();

				// 참여를 표시한 사용자 리스트에 추가 (맵핑 테이블에 데이터 추가)
				Optional<GatheringUser> optGatheringUser = gatheringUserRepository.findByGatheringAndUser(gathering, user);
				if (optGatheringUser.isPresent()) {
					gatheringUserRepository.delete(optGatheringUser.get());

					// peopleNum 감소
					// user 가 female 일 경우 femaleNum 감소
					// user 가 male 일 경우 maleNum 감소
					gathering.minusPeopleNum(user);

					gatheringRepository.save(gathering);

					return GatheringDto.from(gathering);

				} else {
					throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
				}

			} else {
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}

}

package com.sottie.app.gathering.application;

import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.gathering.model.dto.GatheringDto;
import com.sottie.app.gathering.model.record.DeleteGatheringRequest;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.gathering.repository.GatheringUserRepository;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import com.sottie.utils.SottieUserUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteGatheringService {

	private final HttpServletRequest httpServletRequest;
	private final UserRepository userRepository;
	private final GatheringRepository gatheringRepository;
	private final GatheringUserRepository gatheringUserRepository;

	public GatheringDto deleteGathering(DeleteGatheringRequest deleteGatheringRequest) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {

			Optional<Gathering> optGathering = gatheringRepository.findById(deleteGatheringRequest.id());

			if (optGathering.isPresent()) {
				Gathering gathering = optGathering.get();
				checkGatheringHostValidation(gathering, optUser.get());
				List<GatheringUser> gatheringUsers = gatheringUserRepository.findByGatheringId(gathering.getId());

				// 현재 정책은 Host 가 모임글 삭제시 다른 참여자(user)도 취소가 자동으로 이루어짐
				// 다른 참여자에게 모임글이 삭제되었다는 push 알림 필요할듯
				gatheringUserRepository.deleteAll(gatheringUsers);
				gatheringRepository.delete(gathering);

				return GatheringDto.from(gathering);

			} else {
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}

	private void checkGatheringHostValidation(Gathering gathering, User logInUser) {
		if (!gathering.getHost().equals(logInUser.getId())) {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}
}

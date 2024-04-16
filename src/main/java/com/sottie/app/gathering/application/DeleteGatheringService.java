package com.sottie.app.gathering.application;

import com.sottie.app.gathering.adapter.DefaultGatheringRequest;
import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.gathering.model.GenderCategory;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteGatheringService {

	private final HttpServletRequest httpServletRequest;
	private final UserRepository userRepository;
	private final GatheringRepository gatheringRepository;
	private final GatheringUserRepository gatheringUserRepository;

	public void deleteGathering(DefaultGatheringRequest defaultGatheringRequest) {

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
				checkDeleteGatheringValidation(gathering, optUser.get());
				List<GatheringUser> gatheringUsers = gatheringUserRepository.findByGathering(gathering);

				gatheringUserRepository.deleteAll(gatheringUsers);
				gatheringRepository.delete(gathering);

			} else {
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}

	private void checkDeleteGatheringValidation(Gathering gathering, User logInUser) {
		if (!gathering.getHost().equals(logInUser.getId())) {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}
}

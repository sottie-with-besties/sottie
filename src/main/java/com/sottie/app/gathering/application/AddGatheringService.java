package com.sottie.app.gathering.application;

import com.sottie.app.gathering.adapter.DefaultGatheringRequest;
import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GenderCategory;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddGatheringService {

	private final HttpServletRequest httpServletRequest;
	private final UserRepository userRepository;
	private final GatheringRepository gatheringRepository;


	public Gathering addGathering(DefaultGatheringRequest defaultGatheringRequest) {

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
			Gathering gathering = defaultGatheringRequest.to(optUser.get().getId());
			checkAddGatheringValidation(gathering);

			return gatheringRepository.save(gathering);
		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}

	private void checkAddGatheringValidation(Gathering gathering) {
		if (gathering.getGatheringCategory() == null) {
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getTitle().isEmpty()) {
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getLocationId() == null || gathering.getLocationId().equals(0L)) {
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getPeopleNum() == null || gathering.getPeopleNum().equals(0)) {
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getGenderRestriction().equals(GenderCategory.FEMALE)) {
			if ((!Objects.equals(gathering.getFemaleNum(), gathering.getPeopleNum()))) {
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}

		} else if (gathering.getGenderRestriction().equals(GenderCategory.MALE)) {
			if ((!Objects.equals(gathering.getMaleNum(), gathering.getPeopleNum()))) {
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}

		} else if (gathering.getMannerRestrictionYn().equals(Boolean.TRUE)) {
			if (gathering.getMannerTemperature().equals(0)) {
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}
		} else if (gathering.getAgeRestrictionYn().equals(Boolean.TRUE)) {
			if (gathering.getAgeTo().equals(0) && gathering.getAgeFrom().equals(0)) {
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}
		}
	}
}

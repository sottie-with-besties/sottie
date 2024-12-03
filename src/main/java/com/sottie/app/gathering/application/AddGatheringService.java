package com.sottie.app.gathering.application;

import com.sottie.app.gathering.model.record.AddGatheringRequest;
import com.sottie.app.gathering.model.record.DefaultGatheringRequest;
import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GenderCategory;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AddGatheringService {

	private final HttpServletRequest httpServletRequest;
	private final UserRepository userRepository;
	private final GatheringRepository gatheringRepository;


	public Gathering addGathering(AddGatheringRequest addGatheringRequest) {

		// user session
		//TODO 테스트 위한 임시 주석
		HttpSession session = httpServletRequest.getSession(false);
		Long loginUserId = null;
		if (session != null) {
			loginUserId = (Long) session.getAttribute("userId");
		} else {
//			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}


		Gathering gathering = addGatheringRequest.to(0L);
		checkAddGatheringValidation(gathering);

		return gatheringRepository.save(gathering);

		//TODO 테스트 위한 임시 주석
//		Optional<User> optUser = userRepository.findById(loginUserId);
//
//		if (optUser.isPresent()) {
//			Gathering gathering = defaultGatheringRequest.to(optUser.get().getId());
//			checkAddGatheringValidation(gathering);
//
//			return gatheringRepository.save(gathering);
//		} else {
//			Gathering gathering = defaultGatheringRequest.to(0L);
//			checkAddGatheringValidation(gathering);
//
//			return gatheringRepository.save(gathering);
//
//			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
//		}
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

		}else if (gathering.getAgeRestriction().equals(Boolean.TRUE)) {
			if (gathering.getAgeTo().equals(0) && gathering.getAgeFrom().equals(0)) {
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}
		}
	}
}

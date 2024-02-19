package com.sottie.app.gathering.application;

import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.gathering.model.GenderCategory;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.gathering.repository.GatheringUserRepository;
import com.sottie.app.user.model.User;
import com.sottie.errors.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteGatheringService {

	private final GatheringRepository gatheringRepository;
	private final GatheringUserRepository gatheringUserRepository;

	public void deleteGathering(Gathering gathering) {
		// TODO session get user 로 변경 필요
		User user = new User();

		checkDeleteGatheringValidation(gathering, user);

		List<GatheringUser> gatheringUsers = gatheringUserRepository.findByGathering(gathering);
		gatheringUserRepository.deleteAll(gatheringUsers);
		gatheringRepository.delete(gathering);
	}

	private void checkDeleteGatheringValidation(Gathering gathering, User user) {
		if (!gathering.getHost().equals(user)) {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}
}

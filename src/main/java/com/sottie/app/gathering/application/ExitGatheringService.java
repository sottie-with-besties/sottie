package com.sottie.app.gathering.application;

import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.gathering.repository.GatheringUserRepository;
import com.sottie.app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ExitGatheringService {

	private final GatheringRepository gatheringRepository;
	private final GatheringUserRepository gatheringUserRepository;

	public Gathering exitGathering(Gathering gathering) {
		// TODO Session User 가져오기
		User user = new User();

		// peopleNum 감소
		// user 가 female 일 경우 femaleNum 감소
		// user 가 male 일 경우 maleNum 감소
		gathering.minusPeopleNum(user);

		// 참여를 표시한 사용자를 리스트에서 제거 (맵핑 테이블에서 데이터 제거)
		GatheringUser gatheringUser = gatheringUserRepository.findByGatheringAndUser(gathering, user);
		gatheringUserRepository.delete(gatheringUser);

		return gatheringRepository.save(gathering);
	}

}

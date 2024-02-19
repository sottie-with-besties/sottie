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
public class JoinGatheringService {

	private final GatheringRepository gatheringRepository;
	private final GatheringUserRepository gatheringUserRepository;

	public Gathering joinGathering(Gathering gathering) {
		// TODO Session User 가져오기
		User user = new User();

		// peopleNum 증가
		// user 가 female 일 경우 femaleNum 증가
		// user 가 male 일 경우 maleNum 증가
		gathering.plusPeopleNum(user);

		// 참여를 표시한 사용자 리스트에 추가 (맵핑 테이블에 데이터 추가)
		GatheringUser gatheringUser = GatheringUser.mappingGatheringUser(user, gathering);
		gatheringUserRepository.save(gatheringUser);

		return gatheringRepository.save(gathering);
	}

}

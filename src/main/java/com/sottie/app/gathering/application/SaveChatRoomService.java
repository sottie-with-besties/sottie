package com.sottie.app.gathering.application;

import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.dto.GatheringDto;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.errors.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SaveChatRoomService {

	private final GatheringRepository gatheringRepository;

	public GatheringDto saveChatRoom(Long gatheringId, Long chatRoomId) {

		if (chatRoomId != null) {
			Optional<Gathering> byId = gatheringRepository.findById(gatheringId);
			if (byId.isPresent()) {
				Gathering gathering = byId.get();
				gathering.saveChatRoomId(chatRoomId);
				return GatheringDto.from(gathering);
			} else {
				log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
				throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
			}
		} else {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
		}
	}
}

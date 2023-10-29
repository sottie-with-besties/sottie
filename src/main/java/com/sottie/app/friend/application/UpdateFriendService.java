package com.sottie.app.friend.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.friend.model.Friend;
import com.sottie.app.friend.repository.FriendRepository;
import com.sottie.errors.CommonErrorCode;
import com.sottie.errors.CommonException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateFriendService {

	private final FriendRepository repository;

	public void blockFriend(Long userId, Long friendId) {
		Friend friend = repository.findByUserIdAndFriendIdAndBlocked(userId, friendId, false)
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
		friend.setBlocked(true);
	}
}

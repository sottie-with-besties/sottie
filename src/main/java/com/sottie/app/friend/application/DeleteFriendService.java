package com.sottie.app.friend.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.friend.repository.FriendRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteFriendService {

	private final FriendRepository repository;

	public void deleteUnblockedFriend(Long userId, Long friendId) {
		repository.deleteByUserIdAndFriendIdAndBlocked(userId, friendId, false);
	}
}

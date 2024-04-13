package com.sottie.app.friend.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.friend.model.Friend;
import com.sottie.app.friend.repository.FriendRepository;
import com.sottie.app.user.application.GetUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AddFriendService {

	private final FriendRepository repository;
	private final GetUserService getUserService;

	public Friend addNewFriend(Long userId, Long friendId) {

		return repository.save(
			Friend.builder()
				.userId(userId)
				.friendId(friendId)
				.alias(getUserService.getUserById(friendId).getNickName()) //친구 추가할 때 닉네임으로 저장할지 이름으로 저장할 지 확인 필요
				.blocked(false)
				.build()
		);
	}
}

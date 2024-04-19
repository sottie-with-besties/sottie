package com.sottie.app.friend.application;

import com.sottie.app.user.model.User;
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

	private final FriendRepository friendRepository;
	private final GetUserService getUserService;

	public Friend addNewFriend(Long userId, Long friendId) {

		return friendRepository.save(
			Friend.builder()
				.userId(userId)
				.friendId(friendId)
				.alias(getUserService.getUserById(friendId).getNickName()) //친구 추가할 때 alias 기본값은 상대가 설정한 nickName
				.blocked(false)
				.build()
		);
	}
}

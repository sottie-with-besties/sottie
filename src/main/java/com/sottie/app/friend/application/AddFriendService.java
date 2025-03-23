package com.sottie.app.friend.application;

import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import com.sottie.utils.SottieUserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.friend.model.Friend;
import com.sottie.app.friend.repository.FriendRepository;
import com.sottie.app.user.application.GetUserService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddFriendService {

	private final UserRepository userRepository;
	private final FriendRepository friendRepository;
	private final GetUserService getUserService;

	public Friend addNewFriend(Long friendId) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {

			User user = optUser.get();

			return friendRepository.save(
					Friend.builder()
							.userId(user.getId())
							.friendId(friendId)
							.alias(getUserService.getUserById(friendId).getNickName()) //친구 추가할 때 alias 기본값은 상대가 설정한 nickName
							.blocked(false)
							.build()
			);

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}
}

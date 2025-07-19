package com.sottie.app.friend.application;

import com.sottie.app.friend.error.FriendErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.utils.SottieUserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.friend.model.Friend;
import com.sottie.app.friend.repository.FriendRepository;
import com.sottie.errors.CommonErrorCode;
import com.sottie.errors.CommonException;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateFriendService {

	private final UserRepository userRepository;
	private final FriendRepository friendRepository;

	public void blockFriend(Long friendId) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {

			User user = optUser.get();

			Friend friend = findUnblockedFriendByUserIdAndFriendId(user.getId(), friendId);
			friend.setBlocked(true);

		} else {
			throw CommonException.builder(FriendErrorCode.CANNOT_FIND_USER).build();
		}

	}

	public void unblockFriend(Long friendId) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {

			User user = optUser.get();

			Friend friend = findBlockedFriendByUserIdAndFriendId(user.getId(), friendId);
			friend.setBlocked(false);

		} else {
			throw CommonException.builder(FriendErrorCode.CANNOT_FIND_USER).build();
		}

	}

	public Friend changeAlias(Long friendId, String alias) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {

			User user = optUser.get();

			Friend friend = findUnblockedFriendByUserIdAndFriendId(user.getId(), friendId);
			friend.setAlias(alias);

			return friend;

		} else {
			throw CommonException.builder(FriendErrorCode.CANNOT_FIND_USER).build();
		}

	}

	public Friend findUnblockedFriendByUserIdAndFriendId(Long userId, Long friendId) {
		return friendRepository.findByUserIdAndFriendIdAndBlocked(userId, friendId, false)
			.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
	}

	public Friend findBlockedFriendByUserIdAndFriendId(Long userId, Long friendId) {
		return friendRepository.findByUserIdAndFriendIdAndBlocked(userId, friendId, true)
				.orElseThrow(() -> CommonException.builder(CommonErrorCode.RESOURCE_NOT_FOUND).build());
	}
}

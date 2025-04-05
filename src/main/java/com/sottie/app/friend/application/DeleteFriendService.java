package com.sottie.app.friend.application;

import com.sottie.app.friend.error.FriendErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import com.sottie.utils.SottieUserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sottie.app.friend.repository.FriendRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteFriendService {

	private final UserRepository userRepository;
	private final FriendRepository repository;

	public void deleteUnblockedFriend(Long friendId) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {

			User user = optUser.get();

			repository.deleteByUserIdAndFriendIdAndBlocked(user.getId(), friendId, false);

		} else {
			throw CommonException.builder(FriendErrorCode.CANNOT_FIND_USER).build();
		}
	}

}

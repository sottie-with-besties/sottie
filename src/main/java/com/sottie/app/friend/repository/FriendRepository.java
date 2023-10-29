package com.sottie.app.friend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sottie.app.friend.model.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

	List<Friend> findByUserIdAndBlocked(Long userId, boolean blocked);

	List<Friend> findByUserIdAndAliasAndBlocked(Long userId, String alias, boolean blocked);

	void deleteByUserIdAndFriendIdAndBlocked(Long userId, Long friendId, boolean blocked);

	Optional<Friend> findByUserIdAndFriendIdAndBlocked(Long userId, Long friendId, boolean blocked);
}

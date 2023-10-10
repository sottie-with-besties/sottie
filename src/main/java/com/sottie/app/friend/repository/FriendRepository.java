package com.sottie.app.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sottie.app.friend.model.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

}

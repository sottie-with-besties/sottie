package com.sottie.app.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sottie.app.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByEmail(String email);

	Optional<User> findByIdentifier(String id);

	Optional<User> findByEmail(String email);

	Optional<User> findByPhoneNumber(String phoneNumber);

	Boolean existsByNickName(String nickName);
}

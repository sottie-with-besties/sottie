package com.sottie.app.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sottie.app.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findBySnsTypeAndSnsId();
}

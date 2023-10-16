package com.sottie.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sottie.app.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailAndPassword(String email, String password);

	boolean existsByEmail(String email);

	Optional<User> findByIdentifier(String id);
}

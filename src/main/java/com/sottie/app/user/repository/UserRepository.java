package com.sottie.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sottie.app.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

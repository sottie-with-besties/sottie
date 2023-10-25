package com.sottie.app.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sottie.app.profile.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	
}

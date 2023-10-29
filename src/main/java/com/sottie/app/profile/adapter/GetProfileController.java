package com.sottie.app.profile.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.profile.application.GetProfileService;
import com.sottie.app.profile.model.Profile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
class GetProfileController {

	private final GetProfileService service;

	@PostMapping("/sottie/profiles/phone")
	public ResponseEntity<Profile> getProfileByPhone(
		@RequestBody @Valid GetProfileByPhoneRequest getProfileByPhoneRequest) {
		Profile result = service.getProfileByPhone(getProfileByPhoneRequest.phoneNumber());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}

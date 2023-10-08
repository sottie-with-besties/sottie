package com.sottie.app.user.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.user.application.UpdateUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
class UpdateUserController {

	private final UpdateUserService updateUserService;

	@PostMapping("/sottie/users/password_reset")
	public ResponseEntity<Void> resetPassword(@RequestBody @Valid DefaultUserRequest defaultUserRequest) {
		updateUserService.resetPassword(defaultUserRequest.email(), defaultUserRequest.password());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}

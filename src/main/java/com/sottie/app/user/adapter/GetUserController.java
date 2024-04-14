package com.sottie.app.user.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.user.application.GetUserService;
import com.sottie.app.user.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
class GetUserController {

	private final GetUserService userService;

	@PostMapping("/sottie/users/login")
	public ResponseEntity<User> loginUser(@RequestBody @Valid DefaultUserRequest defaultUserRequest) {
		User result = userService.getUserForLogin(defaultUserRequest.email(), defaultUserRequest.password());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PostMapping("/sottie/users/id_exist")
	public boolean checkUserIdExist(@RequestBody @Valid GetUserByEmailRequest getUserByEmailRequest) {
		return userService.isExistingUserByEmail(getUserByEmailRequest.email());

	}

	@PostMapping("/sottie/users/phone")
	public ResponseEntity<UserEmailResponse> findUserByPhoneNumber(
		@RequestBody @Valid GetUserByPhoneRequest getUserByPhoneRequest) {
		UserEmailResponse result = UserEmailResponse.from(
			userService.getUserByPhoneNumber(getUserByPhoneRequest.phoneNumber()));
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PostMapping("/sottie/users/email")
	public ResponseEntity<UserEmailResponse> findUserByUserEmail(
		@RequestBody @Valid GetUserByEmailRequest getUserByEmailRequest) {
		UserEmailResponse result = UserEmailResponse.from(userService.getUserByEmail(getUserByEmailRequest.email()));
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}

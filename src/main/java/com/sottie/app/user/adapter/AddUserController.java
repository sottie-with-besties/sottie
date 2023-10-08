package com.sottie.app.user.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.user.application.AddUserService;
import com.sottie.app.user.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
class AddUserController {

	private final AddUserService addUserService;

	@PostMapping("/sottie/signup/users")
	public ResponseEntity<User> signUpUser(@RequestBody @Valid DefaultUserRequest defaultUserRequest) {
		User result = addUserService.addUserForSignUp(defaultUserRequest.to());
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

}

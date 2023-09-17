package com.sottie.app.user.adapter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sottie.app.user.application.AddUserService;
import com.sottie.app.user.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
class AddUserController {

	private final AddUserService addUserService;

	@PostMapping("/sottie/signup/users")
	public User signUpUser(@RequestBody @Valid SignUpUserRequest signUpUserRequest) {
		return addUserService.addUserForSignUp(signUpUserRequest.to());
	}

}

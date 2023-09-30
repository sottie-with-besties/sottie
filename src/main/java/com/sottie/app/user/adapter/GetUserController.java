package com.sottie.app.user.adapter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sottie.app.user.application.GetUserService;
import com.sottie.app.user.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
class GetUserController {

	private final GetUserService userService;

	@PostMapping("/sottie/login/users")
	public User loginUser(@RequestBody @Valid LoginUserRequest loginUserRequest) {
		return userService.getUserForLogin(loginUserRequest.email(), loginUserRequest.password());
	}
}

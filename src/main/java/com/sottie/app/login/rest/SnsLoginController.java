package com.sottie.app.login.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SnsLoginController {

	@GetMapping(value = "naverLogin")
	public String naverLogin(Model model) {
		return "naverLogin";
	}

	@GetMapping(value = "naverLoginCallback")
	public String naverLoginCallback(Model model) {
		return "naverLoginCallback";
	}

	@GetMapping(value = "kakaoLogin")
	public String kakaoLogin(Model model) {
		return "kakaoLogin";
	}

	@GetMapping(value = "kakaoLoginCallback")
	public String kakaoLoginCallback(Model model) {
		return "kakaoLoginCallback";
	}

}

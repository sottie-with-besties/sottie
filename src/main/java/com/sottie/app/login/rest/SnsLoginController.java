package com.sottie.app.login.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SnsLoginController {

	@GetMapping(value = "snsLogin")
	public String snsLogin(Model model) {
		return "snsLogin";
	}

	@GetMapping(value = "snsLoginCallback")
	public String snsLoginCallback(Model model) {
		return "snsLoginCallback";
	}
}

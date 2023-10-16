package com.sottie.app.sns.adapter;

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

}

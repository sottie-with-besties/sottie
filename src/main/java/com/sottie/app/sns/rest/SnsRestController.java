package com.sottie.app.sns.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;

@RestController
@RequestMapping(value = "/sns")
public class SnsRestController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping(value = "loginProcess")
	public String loginProcess(@RequestParam String snsType, @RequestParam String snsId) {

		// User Table 에서 snsType, snsId 로 기존 회원 여부 확인
		Optional<User> user = userRepository.findBySnsTypeAndSnsId();

		if (user.isPresent()) {
			// 존재하면 로그인 process 진행 후 모집글 리스트 화면으로 이동

			// login method 호출
			return "로그인이 완료된 상태이니 모집글 리스트 화면으로 이동";
		} else {
			// 존재하지 않으면

			// case 1. 회원가입 페이지로 이동
			return "회원가입 페이지로 이동";

			// case 2-1. 회원가입에 본인인증이 필수 라면
			//           회원가입 페이지 또는 본인인증 페이지로 이동
			// return "회원가입 페이지로 이동 또는 본인인증 페이지로 이동";

			// case 2-2 회원가입에 본인인증이 필수가 아니 라면
			//          회원가입 후 본인인증 페이지로 이동

			// 회원가입 method 호출(token 파라미터 받아서 profile 호출 또는 회원정보 파라미터 모두 받아서 사용)
			// return "본인인증 페이지로 이동";
		}
	}
}

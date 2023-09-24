package com.sottie.app.sns.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.sns.dto.NaverProfile;
import com.sottie.app.sns.dto.NaverToken;
import com.sottie.app.sns.dto.ResponseNaver;
import com.sottie.app.sns.feign.NaverFeignClient;

@RestController
@RequestMapping(value = "/naver")
public class NaverRestController {

	@Autowired
	private NaverFeignClient naverFeignClient;

	@GetMapping(value = "getProfile", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseNaver<NaverProfile> getProfile(@RequestParam String token) {
		return naverFeignClient.getProfile("Bearer " + token);
	}

	// {
	// 	"resultcode": "00",
	// 	"message": "success",
	// 	"response": {
	// 	"id": "WtMYDRS9G-EXCg3YL3zrmv74su8-9scWRNAvjiOhjKc",
	// 		"nickname": "butterk****",
	// 		"name": "정용래",
	// 		"email": "butterking2@naver.com",
	// 		"gender": "M",
	// 		"age": "30-39",
	// 		"birthday": "05-31",
	// 		"profile_image": null,
	// 		"birthyear": "1987",
	// 		"mobile": "010-3929-4946"
	//   }
	// }

	@GetMapping(value = "verifyToken", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseNaver<NaverToken> verifyToken(@RequestParam String token, @RequestParam boolean info) {
		return naverFeignClient.verifyToken("Bearer " + token, info);
	}

	// {
	// 	"resultcode": "00",
	// 	"message": "success",
	// 	"response": {
	// 	"token": "AAAAOzx61sUzdPmW-ga3g-Clmkj5K9LIbrPisevVR4E6FMhVH-dwEL981nsEhm6jRPqLacZ5Vh6fUm1tDxmrOF4Ue0U",
	// 		"expire_date": "2023-09-11 00:59:39",
	// 		"allowed_profile": "profile/id,profile/birthyear,profile/mobile,profile/name,profile/naveremail,profile/nickname,profile/gender,profile/birthdate,profile/age"
	//   }
	// }
}

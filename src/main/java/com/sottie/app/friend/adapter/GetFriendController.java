package com.sottie.app.friend.adapter;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.friend.application.GetFriendService;
import com.sottie.app.friend.model.FriendProfile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
class GetFriendController {

	private final GetFriendService getFriendService;

	@GetMapping("/sottie/friends/{userId}")
	public ResponseEntity<List<FriendProfile>> getFriendProfileList(@PathVariable Long userId) {
		List<FriendProfile> result = getFriendService.getFriendProfileList(userId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/sottie/friends/{userId}/alias/{alias}")
	public ResponseEntity<List<FriendProfile>> searchFriendProfileByAlias(
		@PathVariable Long userId, @PathVariable String alias) {
		List<FriendProfile> result = getFriendService.searchFriendProfileListByAlias(userId, alias);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}

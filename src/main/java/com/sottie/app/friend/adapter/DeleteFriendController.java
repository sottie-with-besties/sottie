package com.sottie.app.friend.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.friend.application.DeleteFriendService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
class DeleteFriendController {

	private final DeleteFriendService deleteFriendService;

	@PostMapping("/sottie/friends/delete")
	public ResponseEntity<Void> deleteUnblockedFriend(@RequestBody @Valid DefaultFriendRequest defaultFriendRequest) {
		deleteFriendService.deleteUnblockedFriend(defaultFriendRequest.userId(), defaultFriendRequest.FriendId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}

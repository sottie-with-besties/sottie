package com.sottie.app.friend.adapter;

import com.sottie.app.friend.model.record.ChangeAliasRequest;
import com.sottie.app.friend.model.record.DefaultFriendRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.friend.application.UpdateFriendService;
import com.sottie.app.friend.model.Friend;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
class UpdateFriendController {

	private final UpdateFriendService updateFriendService;

	@PostMapping("/sottie/friends/block")
	public ResponseEntity<Void> blockFriend(@RequestBody @Valid DefaultFriendRequest defaultFriendRequest) {
		updateFriendService.blockFriend(defaultFriendRequest.FriendId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/sottie/friends/unblock")
	public ResponseEntity<Void> unblockFriend(@RequestBody @Valid DefaultFriendRequest defaultFriendRequest) {
		updateFriendService.unblockFriend(defaultFriendRequest.FriendId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/sottie/friends/change-alias")
	public ResponseEntity<Friend> changeAlias(@RequestBody @Valid ChangeAliasRequest changeAliasRequest) {
		Friend result = updateFriendService.changeAlias(changeAliasRequest.FriendId(), changeAliasRequest.alias());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}

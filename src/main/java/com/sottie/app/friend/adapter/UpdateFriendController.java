package com.sottie.app.friend.adapter;

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
		updateFriendService.blockFriend(defaultFriendRequest.userId(), defaultFriendRequest.FriendId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/sottie/friends/change_alias")
	public ResponseEntity<Friend> changeAlias(@RequestBody @Valid ChangeAliasRequest changeAliasRequest) {
		Friend result = updateFriendService.changeAlias(changeAliasRequest.userId(), changeAliasRequest.FriendId(),
			changeAliasRequest.alias());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}

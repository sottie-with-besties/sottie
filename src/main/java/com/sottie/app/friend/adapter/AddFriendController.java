package com.sottie.app.friend.adapter;

import com.sottie.app.friend.model.record.DefaultFriendRequest;
import com.sottie.app.gathering.model.record.DefaultGatheringRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.friend.application.AddFriendService;
import com.sottie.app.friend.model.Friend;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
class AddFriendController {

	private final AddFriendService addFriendService;

	@PostMapping("/sottie/friends")
	public ResponseEntity<Friend> addNewFriend(@RequestBody @Valid DefaultFriendRequest defaultFriendRequest) {
		Friend result = addFriendService.addNewFriend(defaultFriendRequest.FriendId());
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

}

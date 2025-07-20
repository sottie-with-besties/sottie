package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.application.SaveChatRoomService;
import com.sottie.app.gathering.model.dto.GatheringDto;
import com.sottie.security.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Permission(roles = {"USER"})
class SaveGatheringChatRoomController {

    private final SaveChatRoomService saveChatRoomService;

    /**
     * chatRoom 생성 후에 gathering 과 chatRoom 의 mapping 위해서 chat-app 쪽에서 sottie-app 쪽으로 chatRoomId 전송 용도
     * @param gatheringId
     * @param chatRoomId
     * @return
     */
    @PostMapping("/sottie/gathering/chatroom")
    public ResponseEntity<GatheringDto> saveChatRoom(@RequestParam Long gatheringId, @RequestParam Long chatRoomId) {
        GatheringDto result = saveChatRoomService.saveChatRoom(gatheringId, chatRoomId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}

package com.sottie.app.gathering.model.dto;

import com.sottie.app.gathering.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GatheringInvitationDto {

    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Long registeredBy = 0L;

    private Long modifiedBy = 0L;

    private Long userId;

    private Long gatheringId;

    private Long friendUserId;

    private InvitationStatusCategory invitationStatus;

    public static GatheringInvitationDto from(GatheringInvitation gatheringInvitation) {
        return new GatheringInvitationDto(
                gatheringInvitation.getId(),
                gatheringInvitation.getCreatedDate(),
                gatheringInvitation.getModifiedDate(),
                gatheringInvitation.getRegisteredBy(),
                gatheringInvitation.getModifiedBy(),
                gatheringInvitation.getUserId(),
                gatheringInvitation.getGatheringId(),
                gatheringInvitation.getFriendUserId(),
                gatheringInvitation.getInvitationStatus()
        );
    }
}

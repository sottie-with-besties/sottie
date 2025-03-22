package com.sottie.app.gathering.model;

import com.sottie.app.base.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "st_gathering_invitation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GatheringInvitation extends BaseEntity {

    private Long userId;

    private Long gatheringId;

    private Long friendUserId;

    private InvitationStatusCategory invitationStatus;

    public static GatheringInvitation createGatheringInvitation(Long userId, Long gatheringId, Long friendUserId) {
        return GatheringInvitation.builder()
                .userId(userId)
                .gatheringId(gatheringId)
                .friendUserId(friendUserId)
                .invitationStatus(InvitationStatusCategory.WAITING)
                .build();
    }

    public GatheringInvitation reactGatheringInvitation(InvitationStatusCategory invitationStatus) {
        this.setInvitationStatus(invitationStatus);
        return this;
    }

    private void setUserId(Long userId) {
        this.userId = userId;
    }

    private void setGatheringId(Long gatheringId) {
        this.gatheringId = gatheringId;
    }

    private void setFriendUserId(Long friendUserId) {
        this.friendUserId = friendUserId;
    }

    private void setInvitationStatus(InvitationStatusCategory invitationStatus) {
        this.invitationStatus = invitationStatus;
    }
}

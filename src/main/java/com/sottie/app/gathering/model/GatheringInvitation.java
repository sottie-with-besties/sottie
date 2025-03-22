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
}

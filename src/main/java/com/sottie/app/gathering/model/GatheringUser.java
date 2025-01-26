package com.sottie.app.gathering.model;

import com.sottie.app.base.domain.BaseEntity;
import com.sottie.app.gathering.model.dto.GatheringDto;
import com.sottie.app.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "st_gathering_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GatheringUser extends BaseEntity {

    @JoinColumn(name = "user_id")
    private Long userId;

    @JoinColumn(name = "gathering_id")
    private Long gatheringId;

    public static GatheringUser mappingGatheringUser(Long userId, Long gatheringId) {
        return GatheringUser.builder()
                .userId(userId)
                .gatheringId(gatheringId)
                .build();
    }
}

package com.sottie.app.gathering.model;

import com.sottie.app.base.domain.BaseEntity;
import com.sottie.app.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "st_user_gathring_map")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GatheringUser extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_id")
    private Gathering gathering;

    public static GatheringUser mappingGatheringUser(User user, Gathering gathering) {
        GatheringUser gatheringUser = GatheringUser.builder()
                                                            .user(user)
                                                            .gathering(gathering)
                                                            .build();
        return gatheringUser;
    }
}

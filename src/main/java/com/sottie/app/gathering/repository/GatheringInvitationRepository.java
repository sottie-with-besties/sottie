package com.sottie.app.gathering.repository;

import com.sottie.app.gathering.model.GatheringInvitation;
import com.sottie.app.gathering.model.GatheringUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface GatheringInvitationRepository extends JpaRepository<GatheringInvitation, Long>, JpaSpecificationExecutor<GatheringInvitation> {
    Optional<GatheringInvitation> findByUserIdAndGatheringIdAndFriendUserId(Long userId, Long gatheringId, Long friendUserId);
}

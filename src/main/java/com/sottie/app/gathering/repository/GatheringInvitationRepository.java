package com.sottie.app.gathering.repository;

import com.sottie.app.gathering.model.GatheringInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GatheringInvitationRepository extends JpaRepository<GatheringInvitation, Long>, JpaSpecificationExecutor<GatheringInvitation> {

}

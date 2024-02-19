package com.sottie.app.gathering.repository;

import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface GatheringUserRepository extends JpaRepository<GatheringUser, Long>, JpaSpecificationExecutor<GatheringUser> {

    GatheringUser findByGatheringAndUser(Gathering gathering, User user);

    List<GatheringUser> findByGathering(Gathering gathering);
}

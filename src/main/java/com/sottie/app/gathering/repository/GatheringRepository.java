package com.sottie.app.gathering.repository;

import com.sottie.app.gathering.model.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface GatheringRepository extends JpaRepository<Gathering, Long>, JpaSpecificationExecutor<Gathering> {

}

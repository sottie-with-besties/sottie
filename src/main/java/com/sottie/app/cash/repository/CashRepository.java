package com.sottie.app.cash.repository;

import com.sottie.app.cash.model.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CashRepository extends JpaRepository<Cash, Long>, JpaSpecificationExecutor<Cash> {

    Cash findByUserId(Long userId);
}

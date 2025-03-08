package com.sottie.app.cash.repository;

import com.sottie.app.cash.model.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CashLogRepository extends JpaRepository<CashLog, Long>, JpaSpecificationExecutor<CashLog> {

}

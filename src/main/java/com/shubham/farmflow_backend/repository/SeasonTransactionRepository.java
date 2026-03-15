package com.shubham.farmflow_backend.repository;

import com.shubham.farmflow_backend.entity.SeasonTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonTransactionRepository extends JpaRepository<SeasonTransaction, Long> {
    SeasonTransaction findSeasonTransactionById(Long id);

    List<SeasonTransaction> findSeasonTransactionsByCropSeasonId(Long cropSeasonId);
}

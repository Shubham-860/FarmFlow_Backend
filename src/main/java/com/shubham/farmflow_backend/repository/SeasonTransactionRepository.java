package com.shubham.farmflow_backend.repository;

import com.shubham.farmflow_backend.entity.SeasonTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface SeasonTransactionRepository extends JpaRepository<SeasonTransaction, Long> {
    SeasonTransaction findSeasonTransactionById(Long id);

    List<SeasonTransaction> findSeasonTransactionsByCropSeasonId(Long cropSeasonId);

    @Query("""
                 SELECT
                     sum(case when t.type = 'INCOME' then t.amount else 0 end ) as totalIncome,
                     sum(case when t.type = 'EXPENSE' then t.amount else 0 end ) as totalExpense,
                     sum(case when t.type = 'INCOME' then t.amount else 0 end ) -
                     sum(case when t.type = 'EXPENSE' then t.amount else 0 end ) as profit,
                     sum(case when t.paymentStatus='PENDING' then t.amount else 0 end ) as pending
                 from SeasonTransaction t
                 where t.user.id = :userid
            """)
    Map<String, Object> getBalanceByUserId(@Param("userid") Long userid);
    List<SeasonTransaction> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
}

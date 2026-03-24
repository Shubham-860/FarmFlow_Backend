package com.shubham.farmflow_backend.repository;

import com.shubham.farmflow_backend.entity.SeasonTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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

    @Query("""
                SELECT
                    SUM(CASE WHEN t.type = 'INCOME' AND t.paymentStatus = 'RECEIVED' THEN t.amount ELSE 0 END) as totalIncome,
                    SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) as totalExpense,
                    SUM(CASE WHEN t.type = 'INCOME' AND t.paymentStatus = 'RECEIVED' THEN t.amount ELSE 0 END) -
                    SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) as netProfit,
                    SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) -
                    SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) as anticipatedProfit,
                    SUM(CASE WHEN t.type = 'INCOME' AND t.paymentStatus = 'PENDING' THEN t.amount ELSE 0 END) as pendingIncome
                FROM SeasonTransaction t
                WHERE t.user.id = :userId
                    AND (:farmId IS NULL OR t.farm.id = :farmId)
                    AND (:cropSeasonId IS NULL OR t.cropSeason.id = :cropSeasonId)
                    AND (:startDate IS NULL OR t.transactionDate >= :startDate)
                    AND (:endDate IS NULL OR t.transactionDate <= :endDate)
            """)
    Map<String, Object> getFilteredSummary(
            @Param("userId") Long userId,
            @Param("farmId") Long farmId,
            @Param("cropSeasonId") Long cropSeasonId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


    @Query("""
                SELECT t FROM SeasonTransaction t
                WHERE t.user.id = :userId
                    AND (:farmId IS NULL OR t.farm.id = :farmId)
                    AND (:cropSeasonId IS NULL OR t.cropSeason.id = :cropSeasonId)
                    AND (:startDate IS NULL OR t.transactionDate >= :startDate)
                    AND (:endDate IS NULL OR t.transactionDate <= :endDate)
                ORDER BY t.transactionDate DESC
            """)
    List<SeasonTransaction> getFilteredTransactions(
            @Param("userId") Long userId,
            @Param("farmId") Long farmId,
            @Param("cropSeasonId") Long cropSeasonId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
            SELECT t.cropSeason.cropName as cropname,
                   SUM (t.amount) as totalIncome,
                   SUM (t.quantity) as totalQuantity
            from SeasonTransaction t WHERE  t.type = "INCOME"
                        GROUP BY t.cropSeason.cropName
                                    ORDER BY  SUM(t.amount) DESC  LIMIT 5
            """)
    List<Map<String, Object>> getTopFiveCrops();

    @Query("""
                SELECT t FROM SeasonTransaction t
                ORDER BY t.createdAt DESC
                LIMIT 5
            """)
    List<SeasonTransaction> getRecentActivity();

    long count();

    @Query("""
                SELECT
                    SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) as totalIncome,
                    SUM(CASE WHEN t.type = 'INCOME' THEN t.quantity ELSE 0 END) as totalQuantity,
                    COUNT(DISTINCT t.farm.id) as farmCount,
                    SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) as totalExpense
                FROM SeasonTransaction t
                WHERE t.cropSeason.cropName = :cropName
            """)
    Map<String, Object> getCropSummary(@Param("cropName") String cropName);



    @Query("""
                SELECT t.farm.name as farmName,
                       SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) as totalIncome
                FROM SeasonTransaction t
                WHERE t.cropSeason.cropName = :cropName
                GROUP BY t.farm.id, t.farm.name
                ORDER BY totalIncome DESC
                LIMIT 5
            """)
    List<Map<String, Object>> getTopFarmsByCrop(@Param("cropName") String cropName);


    @Query("""
                SELECT t.cropSeason.cropName as cropName
                FROM SeasonTransaction t
                GROUP BY t.cropSeason.cropName
                ORDER BY t.cropSeason.cropName ASC
            """)
    List<String> getAllDistinctCropNames();

    @Query("""
    SELECT t.farm.name as farmName,
           SUM(CASE WHEN t.type = 'INCOME' THEN t.quantity ELSE 0 END) as totalQuantity
    FROM SeasonTransaction t
    WHERE UPPER(t.cropSeason.cropName) = UPPER(:cropName)
    GROUP BY t.farm.id, t.farm.name
    ORDER BY totalQuantity DESC
""")
    List<Map<String, Object>> getProductionByFarm(@Param("cropName") String cropName);

}

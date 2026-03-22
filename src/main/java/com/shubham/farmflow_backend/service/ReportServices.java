package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.dto.SeasonTransactionDTO;
import com.shubham.farmflow_backend.entity.User;
import com.shubham.farmflow_backend.repository.CropSeasonRepository;
import com.shubham.farmflow_backend.repository.FarmRepository;
import com.shubham.farmflow_backend.repository.SeasonTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServices {
    @Autowired
    private CropSeasonRepository csRepository;
    @Autowired
    private SeasonTransactionRepository stRepository;
    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private UserService userService;

    public ResponseEntity<Map<String, Object>> dashboardReport() {
        try {
            User currentUser = userService.getCurrentUser();
            Map<String, Object> result = new HashMap<>(stRepository.getBalanceByUserId(currentUser.getId()));
            result.put("latestTransactions", stRepository.findTop10ByUserIdOrderByCreatedAtDesc(currentUser.getId())
                    .stream()
                    .map(SeasonTransactionDTO::new)
                    .toList()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Something went wrong"));
        }
    }

    public ResponseEntity<Map<String, Object>> getFilterReport(Long farmId, Long cropSeasonId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            User currentUser = userService.getCurrentUser();
            Long userId = currentUser.getId();

            //Summary
            Map<String, Object> result = new HashMap<>(
                    stRepository.getFilteredSummary(userId, farmId, cropSeasonId, startDate, endDate)
            );

            // All Transactions
            List<SeasonTransactionDTO> transaction = stRepository.getFilteredTransactions(userId, farmId, cropSeasonId, startDate, endDate)
                    .stream()
                    .map(SeasonTransactionDTO::new)
                    .toList();

            Map<String, Double> expenseBreakdown = transaction
                    .stream()
                    .filter(t -> t.getType().name().equals("EXPENSE"))
                    .collect(Collectors.groupingBy(
                            t -> t.getCategory().name(),
                            Collectors.summingDouble(SeasonTransactionDTO::getAmount)
                    ));

            long totalSales = transaction.stream()
                    .filter(t -> t.getType().name().equals("INCOME"))
                    .count();

            double avgPrice = transaction.stream()
                    .filter(t -> t.getType().name().equals("INCOME"))
                    .mapToDouble(SeasonTransactionDTO::getPricePerUnit)
                    .average()
                    .orElse(0.0);

            double totalProduction = transaction.stream()
                    .filter(t -> t.getType().name().equals("INCOME"))
                    .mapToDouble(SeasonTransactionDTO::getQuantity)
                    .sum();

            Map<String, Object> report = Map.of(
                    "totalSales", totalSales,
                    "avgPrice", avgPrice,
                    "totalProduction", totalProduction
            );

            result.put("report", report);
            result.put("expenseBreakdown", expenseBreakdown);
            result.put("transactions", transaction);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Something went wrong"));
        }
    }


}

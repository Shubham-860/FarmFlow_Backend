package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.dto.SeasonTransactionDTO;
import com.shubham.farmflow_backend.entity.User;
import com.shubham.farmflow_backend.repository.CropSeasonRepository;
import com.shubham.farmflow_backend.repository.FarmRepository;
import com.shubham.farmflow_backend.repository.SeasonTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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


}

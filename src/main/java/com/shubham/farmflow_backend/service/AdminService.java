package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.dto.SeasonTransactionDTO;
import com.shubham.farmflow_backend.dto.UserInfoDTO;
import com.shubham.farmflow_backend.entity.Enums;
import com.shubham.farmflow_backend.entity.User;
import com.shubham.farmflow_backend.repository.FarmRepository;
import com.shubham.farmflow_backend.repository.SeasonTransactionRepository;
import com.shubham.farmflow_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private SeasonTransactionRepository stRepository;
    @Autowired
    private UserService userService;

    public ResponseEntity<?> getDashboardSummary() {
        try {
            Map<String, Object> result = new HashMap<>();
            result.put("totalUsers", userRepository.countUserByRole(Enums.Role.USER));
            result.put("totalFarms", farmRepository.count());
            result.put("totalTransactions", stRepository.count());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch dashboard summary: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> getTopCrops() {
        try {
            List<Map<String, Object>> topCrops = new java.util.ArrayList<>(
                    stRepository.getTopFiveCrops()
            );
            return ResponseEntity.ok(topCrops);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch top crops: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> getRecentActivity() {
        try {
            List<SeasonTransactionDTO> activity = stRepository.getRecentActivity()
                    .stream()
                    .map(SeasonTransactionDTO::new)
                    .toList();
            return ResponseEntity.ok(activity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch recent activity: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserInfoDTO> users = userRepository.getAllUsers()
                    .stream()
                    .map(UserInfoDTO::new)
                    .toList();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch users: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> deleteUser(Long id) {
        try {
            User currentUser = userService.getCurrentUser();
            User user = userRepository.findUserById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found"));
            }
            if (Objects.equals(user.getId(), currentUser.getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "You cannot delete yourself"));
            }
            userRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete user: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> getCropNames() {
        try {
            List<String> cropNames = stRepository.getAllDistinctCropNames();
            return ResponseEntity.ok(cropNames);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch crop names: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> getCropAnalytics(String cropName) {
        try {
            cropName = cropName.trim().toUpperCase(); // normalize

            Map<String, Object> result = new HashMap<>();
            result.put("summary", stRepository.getCropSummary(cropName));
            result.put("topFarms", stRepository.getTopFarmsByCrop(cropName));
            result.put("productionByFarm", stRepository.getProductionByFarm(cropName));

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch crop analytics: " + e.getMessage()));
        }
    }


}
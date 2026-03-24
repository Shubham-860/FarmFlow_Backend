package com.shubham.farmflow_backend.controller;

import com.shubham.farmflow_backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/summary")
    public ResponseEntity<?> getDashboardSummary() {
        return adminService.getDashboardSummary();
    }

    @GetMapping("/topcops")
    public ResponseEntity<?> getTopCrops() {
        return adminService.getTopCrops();
    }

    @GetMapping("/recentactivity")
    public ResponseEntity<?> getRecentActivity() {
        return adminService.getRecentActivity();
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return adminService.getAllUsers();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }

    @GetMapping("/cropnames")
    public ResponseEntity<?> getCropNames() {
        return adminService.getCropNames();
    }

    @GetMapping("/cropanalytics")
    public ResponseEntity<?> getCropAnalytics(@RequestParam String cropName) {
        return adminService.getCropAnalytics(cropName);
    }

}
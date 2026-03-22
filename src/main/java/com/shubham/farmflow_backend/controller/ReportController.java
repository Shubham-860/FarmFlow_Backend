package com.shubham.farmflow_backend.controller;

import com.shubham.farmflow_backend.dto.FarmWithoutTransectionDTO;
import com.shubham.farmflow_backend.service.FarmService;
import com.shubham.farmflow_backend.service.ReportServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportServices service;

    @Autowired
    private FarmService farmService;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        return service.dashboardReport();
    }

    @GetMapping("/filter")
    public ResponseEntity<Map<String, Object>> getFilterReport(
            @RequestParam(required = false) Long farmId,
            @RequestParam(required = false) Long cropSeasonId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {

        return service.getFilterReport(farmId, cropSeasonId, startDate, endDate);
    }

    @GetMapping("/farms")
    public ResponseEntity<List<FarmWithoutTransectionDTO>> getFarms() {
        return farmService.getFarmsByUserId();
    }
}

package com.shubham.farmflow_backend.controller;

import com.shubham.farmflow_backend.entity.SeasonTransaction;
import com.shubham.farmflow_backend.service.SeasonTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seasontransaction")
public class SeasonTransactionController {
    @Autowired
    private SeasonTransactionService service;

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<SeasonTransaction>> getAllSeasonTransactionsByCropSeasonId(@RequestParam Long cropSeasonId) {
        return ResponseEntity.ok(service.getSeasonTransactionsByCropSeasonId(cropSeasonId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeasonTransaction> getSeasonTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSeasonTransactionById(id));
    }

    @GetMapping("/cs/{cropSeasonId}")
    public ResponseEntity<Iterable<SeasonTransaction>> getSeasonTransactionsByCropSeasonId(@PathVariable Long cropSeasonId) {
        return ResponseEntity.ok(service.getSeasonTransactionsByCropSeasonId(cropSeasonId));
    }

    @PostMapping("/add")
    public ResponseEntity<SeasonTransaction> addSeasonTransaction(@RequestBody SeasonTransaction seasonTransaction) {
        return ResponseEntity.ok(service.addSeasonTransaction(seasonTransaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeasonTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteSeasonTransaction(id));
    }

    @PutMapping("/update")
    public ResponseEntity<SeasonTransaction> updateSeasonTransaction(@RequestBody SeasonTransaction seasonTransaction) {
        return ResponseEntity.ok(service.updateSeasonTransaction(seasonTransaction));
    }

}

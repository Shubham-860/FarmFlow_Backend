package com.shubham.farmflow_backend.controller;

import com.shubham.farmflow_backend.entity.Farm;
import com.shubham.farmflow_backend.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/farm")
public class FarmController {
    @Autowired
    private FarmService service;

    @GetMapping("/{id}")
    public ResponseEntity<Farm> getFarmById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getFarmById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Iterable<Farm>> getFarmsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getFarmsByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<Farm> addFarm(@RequestBody Farm farm) {
        return ResponseEntity.ok(service.addFarm(farm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFarm(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteFarm(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Farm> updateFarm(@RequestBody Farm farm) {
        return ResponseEntity.ok(service.updateFarm(farm));
    }

}

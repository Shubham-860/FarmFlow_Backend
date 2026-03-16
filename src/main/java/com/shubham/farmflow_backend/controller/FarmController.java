package com.shubham.farmflow_backend.controller;

import com.shubham.farmflow_backend.dto.FarmDTO;
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
    public ResponseEntity<FarmDTO> getFarmById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getFarmById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<FarmDTO>> getAllUserFarms() {
        return ResponseEntity.ok(service.getFarmsByUserId());
    }

//    @GetMapping("/me")
//    public ResponseEntity<Iterable<Farm>> getMyFarms() {
//        User currentUser = userService.getCurrentUser();
//        return ResponseEntity.ok(service.getFarmsByUserId(currentUser.getId()));
//    }

    @PostMapping("/add")
    public ResponseEntity<String> addFarm(@RequestBody Farm farm) {
        return ResponseEntity.ok(service.addFarm(farm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id) {
        service.deleteFarm(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmDTO> updateFarm(@PathVariable Long id, @RequestBody Farm farm) {
        farm.setId(id);
        return ResponseEntity.ok(service.updateFarm(farm));
    }

}

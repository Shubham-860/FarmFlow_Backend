package com.shubham.farmflow_backend.controller;

import com.shubham.farmflow_backend.dto.FarmDTO;
import com.shubham.farmflow_backend.dto.FarmWithoutTransectionDTO;
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
        return service.getFarmById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<FarmWithoutTransectionDTO>> getAllUserFarms() {
        return ResponseEntity.ok(service.getFarmsByUserId());
    }

    @GetMapping("/allinfo")
    public ResponseEntity<Iterable<FarmDTO>> getAllUserFarmsData() {
        return ResponseEntity.ok(service.getFarmsWithTransectionsByUserId());
    }

//    @GetMapping("/me")
//    public ResponseEntity<Iterable<Farm>> getMyFarms() {
//        User currentUser = userService.getCurrentUser();
//        return ResponseEntity.ok(service.getFarmsByUserId(currentUser.getId()));
//    }

    @PostMapping("/add")
    public ResponseEntity<String> addFarm(@RequestBody Farm farm) {
        return service.addFarm(farm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFarm(@PathVariable Long id) {
        return service.deleteFarm(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmDTO> updateFarm(@PathVariable Long id, @RequestBody Farm farm) {
        farm.setId(id);
        return service.updateFarm(farm);
    }

}

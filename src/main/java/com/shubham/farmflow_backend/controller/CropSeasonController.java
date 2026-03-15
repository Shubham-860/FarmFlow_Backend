package com.shubham.farmflow_backend.controller;

import com.shubham.farmflow_backend.entity.CropSeason;
import com.shubham.farmflow_backend.service.CropSeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cropseason")
public class CropSeasonController {

    @Autowired
    private CropSeasonService service;

    @GetMapping("/{id}")
    public ResponseEntity<CropSeason> getCropSeasonById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCropSeasonById(id));
    }

    @GetMapping("/farms/{farmId}")
    public ResponseEntity<Iterable<CropSeason>> getCropSeasonsByFarmId(@PathVariable Long farmId) {
        return ResponseEntity.ok(service.getCropSeasonsByFarmId(farmId));
    }

    @PostMapping("/add")
    public ResponseEntity<CropSeason> addCropSeason(@RequestBody CropSeason cropSeason) {
        return ResponseEntity.ok(service.addCropSeason(cropSeason));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCropSeason(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteCropSeason(id));
    }

    @PutMapping("/update")
    public ResponseEntity<CropSeason> updateCropSeason(@RequestBody CropSeason cropSeason) {
        return ResponseEntity.ok(service.updateCropSeason(cropSeason));
    }



}

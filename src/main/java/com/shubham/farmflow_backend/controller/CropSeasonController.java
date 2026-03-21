package com.shubham.farmflow_backend.controller;

import com.shubham.farmflow_backend.dto.CropSeasonDTO;
import com.shubham.farmflow_backend.dto.CropSeasonInfoOnlyDTO;
import com.shubham.farmflow_backend.dto.IdBoolRequestDTO;
import com.shubham.farmflow_backend.dto.SetActiveDTO;
import com.shubham.farmflow_backend.entity.CropSeason;
import com.shubham.farmflow_backend.service.CropSeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cropseason")
public class CropSeasonController {

    @Autowired
    private CropSeasonService service;

    @GetMapping("/{id}")
    public ResponseEntity<CropSeasonDTO> getCropSeasonById(@PathVariable Long id) {
        return service.getCropSeasonById(id);
    }

    @GetMapping("/only/{id}")
    public ResponseEntity<CropSeasonInfoOnlyDTO> getCropSeasonOnlyById(@PathVariable Long id) {
        return service.getCropSeasonOnlyById(id);
    }
//    @GetMapping("/farms/{farmId}")
//    public ResponseEntity<Iterable<CropSeason>> getCropSeasonsByFarmId(@PathVariable Long farmId) {
//        return ResponseEntity.ok(service.getCropSeasonsByFarmId(farmId));
//    }

    @PostMapping("/add")
    public ResponseEntity<String> addCropSeason(@RequestBody CropSeason cropSeason) {
        return service.addCropSeason(cropSeason);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCropSeason(@PathVariable Long id) {
        return service.deleteCropSeason(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CropSeasonDTO> updateCropSeason(@PathVariable Long id, @RequestBody CropSeason cropSeason) {
        cropSeason.setId(id);
        return service.updateCropSeason(cropSeason);
    }

    @PostMapping("/activate")
    public ResponseEntity<String> setActiveCropSeason(@RequestBody SetActiveDTO setActiveDTO) {
        return service.setActiveByCropSeasonId(setActiveDTO.getFarmId(), setActiveDTO.getCropSeasonId());
    }

    @PostMapping("/setcomplete")
    public ResponseEntity<String> setCompleteByCropSeasonId(@RequestBody IdBoolRequestDTO idBool) {
        return service.setCompleteByCropSeasonId(idBool.getId(), idBool.getBool());
    }
}

package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.dto.CropSeasonDTO;
import com.shubham.farmflow_backend.dto.CropSeasonInfoOnlyDTO;
import com.shubham.farmflow_backend.entity.CropSeason;
import com.shubham.farmflow_backend.repository.CropSeasonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CropSeasonService {
    @Autowired
    private CropSeasonRepository repository;


    public ResponseEntity<String> addCropSeason(CropSeason cropSeason) {
        try {
            repository.save(cropSeason);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
        return ResponseEntity.ok("CropSeason added successfully");
    }

//    public Iterable<CropSeason> getCropSeasonsByFarmId(Long farmId) {
//        return repository.findCropSeasonsByFarmId(farmId);
//    }

    public ResponseEntity<CropSeasonDTO> getCropSeasonById(Long id) {
        CropSeason cropSeason = repository.findCropSeasonById(id);
        if (cropSeason == null) {
            ResponseEntity.notFound().build();
            throw new IllegalArgumentException("CropSeason with id " + id + " not found.");
        }
        return ResponseEntity.ok(new CropSeasonDTO(cropSeason));
    }

    public ResponseEntity<CropSeasonInfoOnlyDTO> getCropSeasonOnlyById(Long id) {
        CropSeason cropSeason = repository.findCropSeasonById(id);
        if (cropSeason == null) {
            ResponseEntity.notFound().build();
            throw new IllegalArgumentException("CropSeason with id " + id + " not found.");
        }
        return ResponseEntity.ok(new CropSeasonInfoOnlyDTO(cropSeason));
    }

    public ResponseEntity<String> deleteCropSeason(Long id) {
        CropSeason cropSeason = repository.findCropSeasonById(id);
        if (cropSeason == null) {
            ResponseEntity.status(404).body("CropSeason not found");
        }
        repository.deleteById(id);
        return ResponseEntity.ok("CropSeason deleted successfully");
    }

    public ResponseEntity<CropSeasonDTO> updateCropSeason(CropSeason cropSeason) {
        CropSeason existingCropSeason = repository.findCropSeasonById(cropSeason.getId());
        if (existingCropSeason == null) {
            ResponseEntity.status(404).body("CropSeason not found");
        }
        CropSeason saved = repository.save(cropSeason);
        return ResponseEntity.ok(new CropSeasonDTO(saved));
    }

//    public ResponseEntity<List<CropSeasonInfoOnlyDTO>> getActiveCropSeasonByFarmId(Long farmId) {
//        List cropSessions = repository.findCropSeasonsByFarmIdAndIsActiveTrue(farmId);
//
//    }

    @Transactional
    public ResponseEntity<String> setActiveByCropSeasonId(Long farmId, Long cropSeasonId) {
        CropSeason cropSeason = repository.findCropSeasonById(cropSeasonId);
        if (cropSeason == null) {
            return ResponseEntity.status(404).body("CropSeason not found");
        }
        if (!cropSeason.getFarm().getId().equals(farmId)) {
            return ResponseEntity.status(403).body("CropSeason does not belong to the specified farm");
        }
        try {
            repository.deactivateAllByFarmId(farmId);
            repository.activateById(cropSeasonId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }

        return ResponseEntity.ok("CropSeason set as active successfully");

    }

    @Transactional
    public ResponseEntity<String> setCompleteByCropSeasonId(Long cropSeasonId, boolean isComplete) {
        CropSeason cropSeason = repository.findCropSeasonById(cropSeasonId);
        if (cropSeason == null) {
            return ResponseEntity.status(404).body("CropSeason not found");
        }
        try {
            repository.updateIsCompleteById(isComplete, cropSeasonId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }

        return ResponseEntity.ok("CropSeason set as complete successfully");

    }
}

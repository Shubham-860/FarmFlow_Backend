package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.entity.CropSeason;
import com.shubham.farmflow_backend.repository.CropSeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CropSeasonService {
    @Autowired
    private CropSeasonRepository repository;

    public CropSeason getCropSeasonById(Long id) {
        CropSeason cropSeason = repository.findCropSeasonById(id);
        if (cropSeason == null) {
            throw new IllegalArgumentException("CropSeason with id " + id + " not found.");
        }
        return cropSeason;
    }

    public CropSeason addCropSeason(CropSeason cropSeason) {
        return repository.save(cropSeason);
    }

    public Iterable<CropSeason> getCropSeasonsByFarmId(Long farmId) {
        return repository.findCropSeasonsByFarmId(farmId);
    }

    public void deleteCropSeason(Long id) {
        CropSeason cropSeason = repository.findCropSeasonById(id);
        if (cropSeason == null) {
            throw new IllegalArgumentException("CropSeason with id " + id + " not found.");
        }
        repository.deleteById(id);
    }

    public CropSeason updateCropSeason(CropSeason cropSeason) {
        CropSeason existingCropSeason = repository.findCropSeasonById(cropSeason.getId());
        if (existingCropSeason == null) {
            throw new IllegalArgumentException("CropSeason with id " + cropSeason.getId() + " not found.");
        }
        return repository.save(cropSeason);
    }
}

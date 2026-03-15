package com.shubham.farmflow_backend.repository;

import com.shubham.farmflow_backend.entity.CropSeason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CropSeasonRepository extends JpaRepository<CropSeason, Long> {
    CropSeason findCropSeasonById(Long id);

    List<CropSeason> findCropSeasonsByFarmId(Long farmId);
}

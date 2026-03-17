package com.shubham.farmflow_backend.repository;

import com.shubham.farmflow_backend.entity.CropSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CropSeasonRepository extends JpaRepository<CropSeason, Long> {
    CropSeason findCropSeasonById(Long id);

    List<CropSeason> findCropSeasonsByFarmId(Long farmId);

    @Modifying
    @Query("UPDATE CropSeason c SET  c.isActive = false WHERE c.farm.id = :farmId")
    void deactivateAllByFarmId(Long farmId);

    @Modifying
    @Query("UPDATE CropSeason c SET  c.isActive = true WHERE c.id = :id")
    void activateById(Long id);
}

package com.shubham.farmflow_backend.repository;

import com.shubham.farmflow_backend.entity.CropSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CropSeasonRepository extends JpaRepository<CropSeason, Long> {
    CropSeason findCropSeasonById(Long id);

//    List<CropSeason> findCropSeasonsByFarmId(Long farmId);
//    List<CropSeason> findCropSeasonsByFarmIdAndIsActiveTrue(Long farmId);
//    List<CropSeason> findCropSeasonsByUserIdAndIsActiveTrue(Long userId);

    @Modifying
    @Query("update CropSeason c set c.isComplete = :isComplete where c.id = :id")
    void updateIsCompleteById(@Param("isComplete") boolean isComplete, @Param("id") Long id);

    @Modifying
    @Query("UPDATE CropSeason c SET  c.isActive = false WHERE c.farm.id = :farmId")
    void deactivateAllByFarmId(Long farmId);

    @Modifying
    @Query("UPDATE CropSeason c SET  c.isActive = true WHERE c.id = :id")
    void activateById(Long id);
}

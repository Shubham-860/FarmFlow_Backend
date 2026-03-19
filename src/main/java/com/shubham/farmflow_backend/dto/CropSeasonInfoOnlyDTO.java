package com.shubham.farmflow_backend.dto;

import com.shubham.farmflow_backend.entity.CropSeason;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CropSeasonInfoOnlyDTO {
    private Long id;
    private String cropName;
    private String unit;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isActive;
    private boolean isComplete;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long farmId;
    private String farmName;

    public CropSeasonInfoOnlyDTO(CropSeason cropSeason) {
        this.id = cropSeason.getId();
        this.cropName = cropSeason.getCropName();
        this.unit = cropSeason.getUnit();
        this.startDate = cropSeason.getStartDate();
        this.endDate = cropSeason.getEndDate();
        this.isActive = cropSeason.isActive();
        this.isComplete = cropSeason.isComplete();
        this.notes = cropSeason.getNotes();
        this.createdAt = cropSeason.getCreatedAt();
        this.updatedAt = cropSeason.getUpdatedAt();
        this.farmId = cropSeason.getFarm().getId();
        this.farmName = cropSeason.getFarm().getName();
    }
}
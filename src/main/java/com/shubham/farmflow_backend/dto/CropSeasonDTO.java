package com.shubham.farmflow_backend.dto;

import com.shubham.farmflow_backend.entity.CropSeason;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CropSeasonDTO {
    private Long id;
    private String cropName;
    private String unit;
    private Date startDate;
    private Date endDate;
    private boolean isActive;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long farmId;
    private String farmName;
    private List<SeasonTransactionDTO> seasonTransactions;

    public CropSeasonDTO(CropSeason cropSeason) {
        this.id = cropSeason.getId();
        this.cropName = cropSeason.getCropName();
        this.unit = cropSeason.getUnit();
        this.startDate = cropSeason.getStartDate();
        this.endDate = cropSeason.getEndDate();
        this.isActive = cropSeason.isActive();
        this.notes = cropSeason.getNotes();
        this.createdAt = cropSeason.getCreatedAt();
        this.updatedAt = cropSeason.getUpdatedAt();
        this.farmId = cropSeason.getFarm().getId();
        this.farmName = cropSeason.getFarm().getName();
        this.seasonTransactions = cropSeason.getSeasonTransaction()
                .stream()
                .map(SeasonTransactionDTO::new)
                .collect(Collectors.toList());
    }
}
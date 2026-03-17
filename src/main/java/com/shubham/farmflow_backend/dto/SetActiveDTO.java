package com.shubham.farmflow_backend.dto;

import lombok.Data;

@Data
public class SetActiveDTO {
    private Long farmId;
    private Long cropSeasonId;

    public SetActiveDTO(Long farmId, Long cropSeasonId) {
        this.farmId = farmId;
        this.cropSeasonId = cropSeasonId;
    }
}

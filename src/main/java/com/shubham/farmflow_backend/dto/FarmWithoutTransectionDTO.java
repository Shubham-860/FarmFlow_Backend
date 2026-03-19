package com.shubham.farmflow_backend.dto;

import com.shubham.farmflow_backend.entity.Farm;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FarmWithoutTransectionDTO {
    private Long id;
    private String name;
    private float areaAcre;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String userName;
    private List<CropSeasonInfoOnlyDTO> cropSeasons;


    public FarmWithoutTransectionDTO(Farm farm) {
        this.id = farm.getId();
        this.name = farm.getName();
        this.areaAcre = farm.getAreaAcre();
        this.createdAt = farm.getCreatedAt();
        this.updatedAt = farm.getUpdatedAt();
        this.userId = farm.getUser().getId();
        this.userName = farm.getUser().getName();
        this.cropSeasons = farm.getCropSeasons()
                .stream()
                .map(CropSeasonInfoOnlyDTO::new)
                .collect(Collectors.toList());
    }
}

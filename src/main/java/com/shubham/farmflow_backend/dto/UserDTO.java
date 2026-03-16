package com.shubham.farmflow_backend.dto;

import com.shubham.farmflow_backend.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<FarmDTO> farms;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.farms = user.getFarms()
                .stream()
                .map(FarmDTO::new)
                .collect(Collectors.toList());
    }
}
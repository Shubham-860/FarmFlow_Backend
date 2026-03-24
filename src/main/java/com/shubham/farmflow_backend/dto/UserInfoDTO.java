package com.shubham.farmflow_backend.dto;

import com.shubham.farmflow_backend.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt;

    public UserInfoDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.createdAt = user.getCreatedAt();
    }
}
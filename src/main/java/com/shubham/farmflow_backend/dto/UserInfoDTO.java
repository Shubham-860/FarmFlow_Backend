package com.shubham.farmflow_backend.dto;

import com.shubham.farmflow_backend.entity.User;
import lombok.Data;

@Data
public class UserInfoDTO {
    private Long id;
    private String name;
    private String email;


    public UserInfoDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();

    }
}
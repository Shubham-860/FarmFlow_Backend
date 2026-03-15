package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.entity.Farm;
import com.shubham.farmflow_backend.entity.User;
import com.shubham.farmflow_backend.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmService {
    @Autowired
    FarmRepository repository;

    @Autowired
    UserService userService;

    public Farm addFarm(Farm farm) {
        return repository.save(farm);
    }

    public Farm getFarmById(Long id) {
        Farm farm = repository.findFarmById(id);
        if (farm == null) {
            throw new IllegalArgumentException("Farm with id " + id + " not found.");
        }
        return farm;
    }

    public List<Farm> getFarmsByUserId() {
        User user = userService.getCurrentUser();
        return repository.findFarmsByUserId(user.getId());
    }

    public void deleteFarm(Long id) {
        Farm farm = repository.findById(id).orElse(null);
        if (farm == null) {
            throw new IllegalArgumentException("Farm with id " + id + " not found.");
        }
        repository.deleteById(id);
    }

    public Farm updateFarm(Farm farm) {
        Farm existingFarm = repository.findById(farm.getId()).orElse(null);
        if (existingFarm == null) {
            throw new IllegalArgumentException("Farm with id " + farm.getId() + " not found.");
        }
        return repository.save(farm);
    }
}

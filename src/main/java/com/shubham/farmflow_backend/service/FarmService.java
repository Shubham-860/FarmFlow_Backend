package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.dto.FarmDTO;
import com.shubham.farmflow_backend.entity.Farm;
import com.shubham.farmflow_backend.entity.User;
import com.shubham.farmflow_backend.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FarmService {
    @Autowired
    FarmRepository repository;

    @Autowired
    UserService userService;

    public String addFarm(Farm farm) {
        if (repository.findFarmById(farm.getUser().getId()) != null) {
            throw new IllegalArgumentException("User with id " + farm.getUser().getId() + " already has a farm.");
        }

        User user = userService.getCurrentUser();
        farm.setUser(user);
        repository.save(farm);
        return "Farm added successfully";
    }

    public FarmDTO getFarmById(Long id) {
        Farm farm = repository.findFarmById(id);
        if (farm == null) {
            throw new IllegalArgumentException("Farm with id " + id + " not found.");
        }
        return new FarmDTO(farm);
    }

    public List<FarmDTO> getFarmsByUserId() {
        User user = userService.getCurrentUser();
        return repository.findFarmsByUserId(user.getId())
                .stream()
                .map(FarmDTO::new)
                .collect(Collectors.toList());
    }

    public void deleteFarm(Long id) {
        Farm farm = repository.findById(id).orElse(null);
        if (farm == null) {
            throw new IllegalArgumentException("Farm with id " + id + " not found.");
        }
        repository.deleteById(id);
    }

    public FarmDTO updateFarm(Farm farm) {
        Farm existingFarm = repository.findById(farm.getId()).orElse(null);
        if (existingFarm == null) {
            throw new IllegalArgumentException("Farm with id " + farm.getId() + " not found.");
        }
        Farm saved = repository.save(farm);
        return new FarmDTO(saved);
    }
}

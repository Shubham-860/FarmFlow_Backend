package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.dto.FarmDTO;
import com.shubham.farmflow_backend.dto.FarmWithoutTransectionDTO;
import com.shubham.farmflow_backend.entity.Farm;
import com.shubham.farmflow_backend.entity.User;
import com.shubham.farmflow_backend.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FarmService {
    @Autowired
    FarmRepository repository;

    @Autowired
    UserService userService;

    public ResponseEntity<String> addFarm(Farm farm) {
        User user = userService.getCurrentUser();
        if (repository.findFarmByName(farm.getName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You already have a farm with this name");
        }
        farm.setUser(user);
        repository.save(farm);
        return ResponseEntity.ok("Farm added successfully");
    }

    public ResponseEntity<FarmDTO> getFarmById(Long id) {
        Farm farm = repository.findFarmById(id);
        if (farm == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(new FarmDTO(farm));
    }

    public ResponseEntity<List<FarmWithoutTransectionDTO>> getFarmsByUserId() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok((repository.findFarmsByUserId(user.getId())
                .stream()
                .map(FarmWithoutTransectionDTO::new)
                .collect(Collectors.toList())));
    }

    public List<FarmDTO> getFarmsWithTransectionsByUserId() {
        User user = userService.getCurrentUser();
        return repository.findFarmsByUserId(user.getId())
                .stream()
                .map(FarmDTO::new)
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> deleteFarm(Long id) {
        Farm farm = repository.findById(id).orElse(null);
        if (farm == null) {
            ResponseEntity.status(404).body("Farm not found");
//            throw new IllegalArgumentException("Farm with id " + id + " not found.");
        }
        repository.deleteById(id);
        return ResponseEntity.ok("Farm deleted successfully");
    }

    public ResponseEntity<FarmDTO> updateFarm(Farm farm) {
        Farm existingFarm = repository.findFarmById(farm.getId());
        if (existingFarm == null) {
            ResponseEntity.status(404).body("Farm not found");
//            throw new IllegalArgumentException("Farm with id " + farm.getId() + " not found.");
        }
        assert existingFarm != null;
        farm.setUser(existingFarm.getUser());
        Farm saved = repository.save(farm);
        return ResponseEntity.ok(new FarmDTO(saved));
    }
}

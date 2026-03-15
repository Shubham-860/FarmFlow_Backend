package com.shubham.farmflow_backend.repository;

import com.shubham.farmflow_backend.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    Farm findFarmById(Long id);

    List<Farm> findFarmsByUserId(Long userId);
}

package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.entity.SeasonTransaction;
import com.shubham.farmflow_backend.entity.User;
import com.shubham.farmflow_backend.repository.SeasonTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SeasonTransactionService {

    @Autowired
    private SeasonTransactionRepository repository;
    @Autowired
    private UserService userService;

    public SeasonTransaction getSeasonTransactionById(Long id) {
        SeasonTransaction seasonTransaction = repository.findSeasonTransactionById(id);
        if (seasonTransaction == null) {
            throw new IllegalArgumentException("SeasonTransaction with id " + id + " not found.");
        }
        return seasonTransaction;
    }

    public ResponseEntity<String> addSeasonTransaction(SeasonTransaction seasonTransaction) {
        try {
            User user = userService.getCurrentUser();
            seasonTransaction.setUser(user);
            repository.save(seasonTransaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
        return ResponseEntity.ok("SeasonTransaction added successfully");
    }

    public Iterable<SeasonTransaction> getSeasonTransactionsByCropSeasonId(Long cropSeasonId) {
        return repository.findSeasonTransactionsByCropSeasonId(cropSeasonId);
    }

    public void deleteSeasonTransaction(Long id) {
        SeasonTransaction seasonTransaction = repository.findSeasonTransactionById(id);
        if (seasonTransaction == null) {
            throw new IllegalArgumentException("SeasonTransaction with id " + id + " not found.");
        }
        repository.deleteById(id);
    }

    public SeasonTransaction updateSeasonTransaction(SeasonTransaction seasonTransaction) {
        SeasonTransaction existingSeasonTransaction = repository.findSeasonTransactionById(seasonTransaction.getId());
        if (existingSeasonTransaction == null) {
            throw new IllegalArgumentException("SeasonTransaction with id " + seasonTransaction.getId() + " not found.");
        }
        return repository.save(seasonTransaction);
    }
}

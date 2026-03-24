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

    public ResponseEntity<SeasonTransaction> updateSeasonTransaction(SeasonTransaction st) {
        SeasonTransaction user = repository.findSeasonTransactionById(st.getId());
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            user.setCategory(st.getCategory());
            user.setSourceOrBuyer(st.getSourceOrBuyer());
            user.setQuantity(st.getQuantity());
            user.setUnit(st.getUnit());
            user.setPricePerUnit(st.getPricePerUnit());
            user.setAmount(st.getAmount());
            user.setPaymentStatus(st.getPaymentStatus());
            user.setDescription(st.getDescription());
            user.setTransactionDate(st.getTransactionDate());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repository.save(user));
    }
}

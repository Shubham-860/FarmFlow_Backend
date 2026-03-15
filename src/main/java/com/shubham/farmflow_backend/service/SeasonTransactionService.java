package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.entity.SeasonTransaction;
import com.shubham.farmflow_backend.repository.SeasonTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeasonTransactionService {

    @Autowired
    private SeasonTransactionRepository repository;

    public SeasonTransaction getSeasonTransactionById(Long id) {
        return repository.findSeasonTransactionById(id);
    }

    public SeasonTransaction addSeasonTransaction(SeasonTransaction seasonTransaction) {
        return repository.save(seasonTransaction);
    }

    public Iterable<SeasonTransaction> getSeasonTransactionsByCropSeasonId(Long cropSeasonId) {
        return repository.findSeasonTransactionsByCropSeasonId(cropSeasonId);
    }

    public String deleteSeasonTransaction(Long id) {
        SeasonTransaction seasonTransaction = repository.findSeasonTransactionById(id);
        if (seasonTransaction == null) {
            throw new IllegalArgumentException("SeasonTransaction with id " + id + " not found.");
        }
        String name = seasonTransaction.getCropSeason().getCropName();
        repository.deleteById(id);
        return "SeasonTransaction with id " + name + " has been deleted.";
    }

    public SeasonTransaction updateSeasonTransaction(SeasonTransaction seasonTransaction) {
        SeasonTransaction existingSeasonTransaction = repository.findSeasonTransactionById(seasonTransaction.getId());
        if (existingSeasonTransaction == null) {
            throw new IllegalArgumentException("SeasonTransaction with id " + seasonTransaction.getId() + " not found.");
        }
        return repository.save(seasonTransaction);
    }
}

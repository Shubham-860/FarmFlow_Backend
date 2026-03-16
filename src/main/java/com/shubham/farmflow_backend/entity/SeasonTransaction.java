package com.shubham.farmflow_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table(name = "season_transactions")
@Entity
@Data
public class SeasonTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Enums.TransactionType type;

    @Enumerated(EnumType.STRING)
    private Enums.ExpenseCategory category;

    private String sourceOrBuyer;

    private double quantity;

    private String unit;

    private double pricePerUnit;

    private double amount;

    @Enumerated(EnumType.STRING)
    private Enums.PaymentStatus paymentStatus;

    private String description;

    private LocalDateTime transactionDate;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "cropSeason_id", nullable = false)
    private CropSeason cropSeason;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

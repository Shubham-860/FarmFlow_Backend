package com.shubham.farmflow_backend.dto;

import com.shubham.farmflow_backend.entity.Enums;
import com.shubham.farmflow_backend.entity.SeasonTransaction;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeasonTransactionDTO {
    private Long id;
    private Enums.TransactionType type;
    private Enums.ExpenseCategory category;
    private String sourceOrBuyer;
    private double quantity;
    private String unit;
    private double pricePerUnit;
    private double amount;
    private Enums.PaymentStatus paymentStatus;
    private String description;
    private LocalDateTime transactionDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long cropSeasonId;
    private String cropName;
    private Long userId;

    public SeasonTransactionDTO(SeasonTransaction st) {
        this.id = st.getId();
        this.type = st.getType();
        this.category = st.getCategory();
        this.sourceOrBuyer = st.getSourceOrBuyer();
        this.quantity = st.getQuantity();
        this.unit = st.getUnit();
        this.pricePerUnit = st.getPricePerUnit();
        this.amount = st.getAmount();
        this.paymentStatus = st.getPaymentStatus();
        this.description = st.getDescription();
        this.transactionDate = st.getTransactionDate();
        this.createdAt = st.getCreatedAt();
        this.updatedAt = st.getUpdatedAt();
        this.cropSeasonId = st.getCropSeason().getId();
        this.cropName = st.getCropSeason().getCropName();
        this.userId = st.getUser().getId();
    }
}
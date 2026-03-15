package com.shubham.farmflow_backend.entity;

public class Enums {
    public enum TransactionType {
        INCOME, EXPENSE
    }

    public enum PaymentStatus {
        PENDING, RECEIVED
    }

    public enum ExpenseCategory {
        SEEDS,
        FERTILIZER,
        PESTICIDE,
        LABOUR,
        MACHINERY,
        IRRIGATION,
        TRANSPORT,
        MAINTENANCE,
        OTHER
    }
}

//const expenseCategories = [
//        "Seeds",
//        "Fertilizer",
//        "Pesticide",
//        "Labour",
//        "Machinery",
//        "Irrigation",
//        "Transport",
//        "Maintenance",
//        "Other"
//        ];
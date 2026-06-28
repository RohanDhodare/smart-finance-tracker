package com.rohan.finance_tracker.transaction;

import com.rohan.finance_tracker.categories.Category;
import com.rohan.finance_tracker.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private TransactionType transactionType;

    private String merchantName;

    private String remarks;

    private LocalDate transactionDate;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



}

package com.manager.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private double rateOfInterest;

    @Enumerated(EnumType.STRING)
    private InterestEvery interestEvery;

    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String remarks;
    
}

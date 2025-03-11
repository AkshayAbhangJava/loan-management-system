package com.manager.dto;

import java.time.LocalDate;

import com.manager.entity.InterestEvery;
import com.manager.entity.LoanType;
import lombok.Data;

@Data
public class LoanDTO {
    private double amount;
    private double rateOfInterest;
    private InterestEvery interestEvery;
    private LocalDate startDate;
    private LoanType loanType;
    private String remarks;

}

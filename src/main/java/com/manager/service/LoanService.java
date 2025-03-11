package com.manager.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.manager.entity.Loan;
import com.manager.entity.User;
import com.manager.repository.LoanRepository;
import com.manager.repository.UserRepository;

@Service
public class LoanService {
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public double getTotalOutstandingAmount() {
        return loanRepository.findAll().stream()
                .mapToDouble(loan -> loan.getAmount() + calculateTotalInterest(loan))
                .sum();
    }

    private double calculateTotalInterest(Loan loan) {
        double principal = loan.getAmount();
        double rate = loan.getRateOfInterest() / 100;
        long daysBetween = LocalDate.now().toEpochDay() - loan.getStartDate().toEpochDay();

        switch (loan.getInterestEvery()) {
            case MONTHLY_INTEREST:
                return principal * rate * (daysBetween / 30.0);
            case DAY_WISE:
                return principal * rate * (daysBetween / 365.0);
            case COMPOUND_MONTHLY:
                return principal * Math.pow((1 + rate / 12), daysBetween / 30.0) - principal;
            case COMPOUND_THREE_MONTHLY:
                return principal * Math.pow((1 + rate / 4), daysBetween / 90.0) - principal;
            case COMPOUND_YEARLY:
                return principal * Math.pow((1 + rate), daysBetween / 365.0) - principal;
            default:
                return 0;
        }
    }
}

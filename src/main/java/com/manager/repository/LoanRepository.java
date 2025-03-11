package com.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manager.entity.Loan;
import com.manager.entity.User;

public interface LoanRepository extends JpaRepository<Loan, Long> {}

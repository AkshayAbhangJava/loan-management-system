package com.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manager.entity.Customer;
import com.manager.entity.User;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByPhoneAndUserPhoneNumber(String phone, String userPhoneNumber);
    
    List<Customer> findByUserPhoneNumber(String userPhoneNumber);
}
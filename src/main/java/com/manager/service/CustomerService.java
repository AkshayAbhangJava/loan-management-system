package com.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manager.dto.AttachmentDTO;
import com.manager.dto.CustomerDTO;
import com.manager.dto.LoanDTO;
import com.manager.entity.Attachment;
import com.manager.entity.Customer;
import com.manager.entity.Loan;
import com.manager.repository.CustomerRepository;
import jakarta.transaction.Transactional;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerDTO addCustomer(String userPhoneNumber, CustomerDTO customerDTO) {
        // Check if customer already exists for the logged-in user
        if (customerRepository.existsByPhoneAndUserPhoneNumber(customerDTO.getPhone(), userPhoneNumber)) {
            throw new IllegalArgumentException("Customer with this phone number already exists for this user");
//            todo->>> make a enum exception class with key and value pair and user here !!!!!!!!!!!!!!!!!
        }

        Customer customer = new Customer();										//todo-> use mapper class
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        customer.setUserPhoneNumber(userPhoneNumber);  // Associate with logged-in user

        List<Loan> loans = new ArrayList<>();
        for (LoanDTO loanDTO : customerDTO.getLoans()) {
            Loan loan = new Loan();																
            loan.setAmount(loanDTO.getAmount());
            loan.setRateOfInterest(loanDTO.getRateOfInterest());
            loan.setInterestEvery(loanDTO.getInterestEvery());
            loan.setStartDate(loanDTO.getStartDate());
            loan.setLoanType(loanDTO.getLoanType());
            loan.setRemarks(loanDTO.getRemarks());
            loan.setCustomer(customer);
            loans.add(loan);
        }
        customer.setLoans(loans);

        List<Attachment> attachments = new ArrayList<>();
        for (AttachmentDTO attachmentDTO : customerDTO.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setFileName(attachmentDTO.getFileName());
            attachment.setFileUrl(attachmentDTO.getFileUrl());
            attachment.setCustomer(customer);
            attachments.add(attachment);
        }
        customer.setAttachments(attachments);

        customerRepository.save(customer);
        return customerDTO;
    }

    public List<CustomerDTO> getAllCustomersForUser(String userPhoneNumber) {
        List<Customer> customers = customerRepository.findByUserPhoneNumber(userPhoneNumber);
        return customers.stream().map(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setName(customer.getName());
            customerDTO.setPhone(customer.getPhone());
            customerDTO.setAddress(customer.getAddress());

            List<LoanDTO> loans = customer.getLoans().stream().map(loan -> {
                LoanDTO loanDTO = new LoanDTO();
                loanDTO.setAmount(loan.getAmount());
                loanDTO.setRateOfInterest(loan.getRateOfInterest());
                loanDTO.setInterestEvery(loan.getInterestEvery());
                loanDTO.setStartDate(loan.getStartDate());
                loanDTO.setLoanType(loan.getLoanType());
                loanDTO.setRemarks(loan.getRemarks());
                return loanDTO;
            }).toList();
            customerDTO.setLoans(loans);

            List<AttachmentDTO> attachments = customer.getAttachments().stream().map(attachment -> {
                AttachmentDTO attachmentDTO = new AttachmentDTO();
                attachmentDTO.setFileName(attachment.getFileName());
                attachmentDTO.setFileUrl(attachment.getFileUrl());
                return attachmentDTO;
            }).toList();
            customerDTO.setAttachments(attachments);

            return customerDTO;
        }).toList();
    }
}

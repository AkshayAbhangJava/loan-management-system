package com.manager.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manager.dto.CustomerDTO;
import com.manager.service.CustomerService;
import com.manager.service.LoanService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoanService loanService;

    public CustomerController(CustomerService customerService, LoanService loanService) {
        this.customerService = customerService;
		this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestHeader("User-Phone") String userPhoneNumber,
                                                   @RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = customerService.addCustomer(userPhoneNumber, customerDTO);
        return ResponseEntity.ok(savedCustomer);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(@RequestHeader("User-Phone") String userPhoneNumber) {
        List<CustomerDTO> customers = customerService.getAllCustomersForUser(userPhoneNumber);
        return ResponseEntity.ok(customers);
    }



    @GetMapping("/lent-amounts")
    public ResponseEntity<Double> getTotalOutstandingAmount() {
        double totalOutstandingAmount = loanService.getTotalOutstandingAmount();
        return ResponseEntity.ok(totalOutstandingAmount);
    }
}

package com.ecommerce.oms.controller;

import com.ecommerce.oms.entity.Customer;
import com.ecommerce.oms.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepo;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerRepo.save(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                customerRepo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"))
        );
    }
}

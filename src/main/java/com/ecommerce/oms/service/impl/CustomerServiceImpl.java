package com.ecommerce.oms.service.impl;

import com.ecommerce.oms.entity.Customer;
import com.ecommerce.oms.exception.NotFoundException;
import com.ecommerce.oms.repo.CustomerRepository;
import com.ecommerce.oms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;

    @Override
    public Customer save(Customer c) {
        return repo.findByEmail(c.getEmail())
                .map(existing -> {
                    existing.setName(c.getName());
                    existing.setPhone(c.getPhone());
                    existing.setAddress(c.getAddress());
                    return repo.save(existing);
                })
                .orElse(repo.save(c));
    }

    @Override
    public Customer getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id " + id));
    }

    @Override
    public List<Customer> getAll() {
        return repo.findAll();
    }
}

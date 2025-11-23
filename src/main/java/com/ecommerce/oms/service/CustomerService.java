package com.ecommerce.oms.service;

import com.ecommerce.oms.entity.Customer;
import java.util.List;

public interface CustomerService {
    Customer save(Customer c);
    Customer getById(Long id);
    List<Customer> getAll();
}

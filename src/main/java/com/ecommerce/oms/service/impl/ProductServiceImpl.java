package com.ecommerce.oms.service.impl;

import com.ecommerce.oms.entity.Product;
import com.ecommerce.oms.repo.ProductRepository;
import com.ecommerce.oms.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;
    @Override public Product save(Product p) { return repo.save(p); }
    @Override public Product getById(Long id) { return repo.findById(id).orElse(null); }
    @Override public List<Product> getAll() { return repo.findAll(); }
}

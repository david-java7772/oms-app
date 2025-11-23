package com.ecommerce.oms.controller;

import com.ecommerce.oms.entity.Product;
import com.ecommerce.oms.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepo;

    @PostMapping("/bulk")
    public ResponseEntity<String> bulkInsert(@RequestBody List<Product> products) {
        productRepo.saveAll(products);
        return ResponseEntity.ok("Bulk products inserted successfully");
    }

    @GetMapping
    public List<Product> getAll() {
        return productRepo.findAll();
    }
}

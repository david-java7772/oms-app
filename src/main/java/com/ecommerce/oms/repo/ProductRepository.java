package com.ecommerce.oms.repo;

import com.ecommerce.oms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}

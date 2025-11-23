package com.ecommerce.oms.service;

import com.ecommerce.oms.entity.Product;
import java.util.List;

public interface ProductService {
    Product save(Product p);
    Product getById(Long id);
    List<Product> getAll();
}

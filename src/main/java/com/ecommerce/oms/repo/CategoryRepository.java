package com.ecommerce.oms.repo;

import com.ecommerce.oms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}

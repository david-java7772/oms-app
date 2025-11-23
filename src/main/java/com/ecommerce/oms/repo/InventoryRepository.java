package com.ecommerce.oms.repo;

import com.ecommerce.oms.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {}

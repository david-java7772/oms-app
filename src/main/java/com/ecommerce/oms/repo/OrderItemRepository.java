package com.ecommerce.oms.repo;

import com.ecommerce.oms.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}

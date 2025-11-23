package com.ecommerce.oms.repo;

import com.ecommerce.oms.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdempotencyKey(String key);
    List<Order> findByCustomerId(Long customerId);
}

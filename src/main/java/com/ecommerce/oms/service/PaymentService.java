package com.ecommerce.oms.service;

import com.ecommerce.oms.entity.Order;

public interface PaymentService {
    boolean charge(Order order);
}

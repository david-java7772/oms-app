package com.ecommerce.oms.service.impl;

import com.ecommerce.oms.entity.Order;
import com.ecommerce.oms.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final Random rnd = new Random();
    @Override
    public boolean charge(Order order) {
        // simulate quick charge; no sleeps
        return rnd.nextInt(100) < 90;
    }
}

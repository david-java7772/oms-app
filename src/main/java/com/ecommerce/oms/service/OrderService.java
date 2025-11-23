package com.ecommerce.oms.service;

import com.ecommerce.oms.dto.OrderResponse;
import com.ecommerce.oms.dto.PlaceOrderRequest;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(PlaceOrderRequest req);
    OrderResponse updateStatus(Long id, String status);
    OrderResponse getOrderById(Long id);
    List<OrderResponse> getAllOrders();
    List<OrderResponse> getOrdersByCustomer(Long customerId);
    void deleteOrder(Long id);
}

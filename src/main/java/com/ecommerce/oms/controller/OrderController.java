package com.ecommerce.oms.controller;

import com.ecommerce.oms.dto.OrderResponse;
import com.ecommerce.oms.dto.PlaceOrderRequest;
import com.ecommerce.oms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Create Order
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody PlaceOrderRequest req) {
        return ResponseEntity.ok(orderService.placeOrder(req));
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Get one order
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // Get orders by customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }

    // Update Status
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }

    // Delete Order
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully.");
    }
}

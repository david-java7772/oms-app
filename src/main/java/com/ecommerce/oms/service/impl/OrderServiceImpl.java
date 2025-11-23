package com.ecommerce.oms.service.impl;

import com.ecommerce.oms.dto.OrderItemResponse;
import com.ecommerce.oms.dto.OrderResponse;
import com.ecommerce.oms.dto.PlaceOrderRequest;
import com.ecommerce.oms.entity.Order;
import com.ecommerce.oms.entity.OrderItem;
import com.ecommerce.oms.entity.Customer;
import com.ecommerce.oms.exception.BusinessException;
import com.ecommerce.oms.exception.NotFoundException;
import com.ecommerce.oms.repo.OrderRepository;
import com.ecommerce.oms.repo.ProductRepository;
import com.ecommerce.oms.repo.CustomerRepository;
import com.ecommerce.oms.service.InventoryService;
import com.ecommerce.oms.service.OrderService;
import com.ecommerce.oms.service.PaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final CustomerRepository customerRepo;
    private final InventoryService inventoryService;
    private final PaymentService paymentService;

    @Override
    @Transactional
    public OrderResponse placeOrder(PlaceOrderRequest req) {

        // idempotency check
        if (req.idempotencyKey() != null) {
            orderRepo.findByIdempotencyKey(req.idempotencyKey()).ifPresent(o -> {
                throw new BusinessException("Duplicate Order Key: " + req.idempotencyKey());
            });
        }

        // fetch customer
        Customer cust = customerRepo.findById(req.customerId())
                .orElseThrow(() -> new NotFoundException("Customer not found: " + req.customerId()));

        // reserve inventory
        req.items().forEach(i -> inventoryService.reserve(i.productId(), i.qty()));

        // build order
        Order order = new Order();
        order.setCustomer(cust);
        order.setStatus("PENDING");
        order.setCreatedAt(Instant.now());
        order.setIdempotencyKey(req.idempotencyKey());

        List<OrderItem> items = req.items().stream()
                .map(i -> new OrderItem(null, i.productId(), i.qty(), i.price()))
                .collect(Collectors.toList());

        order.setItems(items);

        Order saved = orderRepo.save(order);

        // payment
        boolean paid = paymentService.charge(saved);
        if (paid) {
            saved.setStatus("CONFIRMED");
        } else {
            saved.setStatus("FAILED");
            saved.getItems().forEach(it -> inventoryService.release(it.getProductId(), it.getQty()));
        }

        Order finalOrder = orderRepo.save(saved);

        return mapToResponse(finalOrder);
    }

    @Override
    @Transactional
    public OrderResponse updateStatus(Long id, String status) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));

        if ("FAILED".equalsIgnoreCase(status) || "CANCELLED".equalsIgnoreCase(status)) {
            order.getItems().forEach(i -> inventoryService.release(i.getProductId(), i.getQty()));
        }
        order.setStatus(status);
        return mapToResponse(orderRepo.save(order));
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found " + id));
        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getOrdersByCustomer(Long customerId) {
        return orderRepo.findByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found " + id));
        if (!"CONFIRMED".equalsIgnoreCase(order.getStatus())) {
            order.getItems().forEach(i -> inventoryService.release(i.getProductId(), i.getQty()));
        }
        orderRepo.delete(order);
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(i -> new OrderItemResponse(i.getProductId(), i.getQty(), i.getPrice()))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getCustomer().getEmail(),
                itemResponses
        );
    }
}

package com.ecommerce.oms.kafka;

import com.ecommerce.oms.entity.Order;
import com.ecommerce.oms.exception.BusinessException;
import com.ecommerce.oms.repo.OrderRepository;
import com.ecommerce.oms.service.InventoryService;
import com.ecommerce.oms.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService; // simulate payment
    private final InventoryService inventoryService;

    @KafkaListener(topics = "order.placed", groupId = "oms-group")
    @Transactional
    public void handleOrderPlaced(String orderIdStr) {
        Long orderId = Long.valueOf(orderIdStr);
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isEmpty()) return;
        Order order = opt.get();

        try {
            // 1) Simulate payment. If payment fails -> mark FAILED and release inventory.
            boolean paid = paymentService.charge(order);
            if (!paid) {
                order.setStatus("FAILED");
                orderRepository.save(order);
                // inventory release handled in OrderService.updateStatus or here:
                order.getItems().forEach(i -> inventoryService.release(i.getProductId(), i.getQty()));
                return;
            }

            // 2) on success set CONFIRMED
            order.setStatus("CONFIRMED");
            orderRepository.save(order);

            // 3) publish other events (e.g., fulfillment) - left as TODO for architect
        } catch (Exception ex) {
            // mark failed and release inventory
            order.setStatus("FAILED");
            orderRepository.save(order);
            order.getItems().forEach(i -> inventoryService.release(i.getProductId(), i.getQty()));
        }
    }
}

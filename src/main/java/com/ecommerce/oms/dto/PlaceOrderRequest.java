package com.ecommerce.oms.dto;

import java.util.List;

public record PlaceOrderRequest(
        String idempotencyKey,
        Long customerId,
        List<OrderItemDto> items
) {}

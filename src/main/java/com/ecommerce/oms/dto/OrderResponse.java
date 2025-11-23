package com.ecommerce.oms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private String status;
    private Instant createdAt;

    private Long customerId;
    private String customerName;
    private String customerEmail;

    private List<OrderItemResponse> items;
}

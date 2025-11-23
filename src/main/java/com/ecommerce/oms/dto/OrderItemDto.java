package com.ecommerce.oms.dto;

import java.math.BigDecimal;

public record OrderItemDto(Long productId, Integer qty, BigDecimal price) {}

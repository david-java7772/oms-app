package com.ecommerce.oms.dto;

import java.math.BigDecimal;

public class OrderItemResponse {
    private Long productId;
    private Integer qty;
    private BigDecimal price;

    public OrderItemResponse(Long productId, Integer qty, BigDecimal price) {
        this.productId = productId;
        this.qty = qty;
        this.price = price;
    }

    public Long getProductId() { return productId; }
    public Integer getQty() { return qty; }
    public BigDecimal getPrice() { return price; }

    public void setProductId(Long productId) { this.productId = productId; }
    public void setQty(Integer qty) { this.qty = qty; }
    public void setPrice(BigDecimal price) { this.price = price; }
}

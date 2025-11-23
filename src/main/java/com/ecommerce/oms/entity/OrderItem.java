package com.ecommerce.oms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Data @NoArgsConstructor @AllArgsConstructor
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    private Integer qty;
    private java.math.BigDecimal price;
}

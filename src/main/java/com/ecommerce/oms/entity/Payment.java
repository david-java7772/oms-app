package com.ecommerce.oms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.math.BigDecimal;

@Entity
@Table(name = "payment")
@Data @NoArgsConstructor @AllArgsConstructor
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    private BigDecimal amount;
    private String status;
    private String method;

    @Column(name = "created_at")
    private Instant createdAt;
}

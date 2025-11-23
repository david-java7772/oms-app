package com.ecommerce.oms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @Column(name = "product_id")
    private Long productId;   // FK to products table

    @Column(nullable = false)
    private Integer availableQty;

    @Column(nullable = false)
    private Integer reservedQty;

    @Version
    private Long version;     // Optimistic locking for concurrency

    @Column(nullable = false)
    private Instant updatedAt;
}

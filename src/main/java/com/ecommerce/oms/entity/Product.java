package com.ecommerce.oms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private Long id;

    private String name;
    private Double price;
    private Integer availableQty;
}

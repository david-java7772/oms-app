package com.ecommerce.oms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "customers")
@JsonIgnoreProperties({"orders"})  // prevent infinite loop
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders;
}

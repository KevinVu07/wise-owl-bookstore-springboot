package com.wiseowl.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String orderDate;
    private double totalPrice;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

}


package com.wiseowl.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Many items belong to one order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "book_image")
    private String bookImage;

    @Column(name = "book_price", nullable = false)
    private Double bookPrice;

    @Column(name = "order_qty", nullable = false)
    private Integer orderQty;

    @Column(name = "order_total", nullable = false)
    private Double orderTotal;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;
}


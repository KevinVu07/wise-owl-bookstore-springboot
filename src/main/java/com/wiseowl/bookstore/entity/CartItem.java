package com.wiseowl.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "book_id")
    private int bookId;

    @Column(name = "book_qty")
    private int bookQty;

    @Column(name = "total")
    private double total;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Book book;

    public double getTotal() {
        return book.getSalePrice() * bookQty;
    }

}


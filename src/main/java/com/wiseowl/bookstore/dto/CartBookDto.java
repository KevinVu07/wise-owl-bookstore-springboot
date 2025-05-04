package com.wiseowl.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartBookDto {
    private int bookId;
    private String image;
    private String name;
    private String type;
    private String description;
    private double salePrice;
    private int qty;
    private double total;
}

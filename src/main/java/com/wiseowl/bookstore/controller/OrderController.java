package com.wiseowl.bookstore.controller;

import com.wiseowl.bookstore.entity.OrderItem;
import com.wiseowl.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create-order")
    public String createOrder(Long userId, List<OrderItem> orderItems) {
        orderService.createOrder(userId, orderItems);
        return "redirect:/orders"; // Redirect to the orders page after creating the order
    }
}


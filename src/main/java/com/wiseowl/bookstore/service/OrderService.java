package com.wiseowl.bookstore.service;

import com.wiseowl.bookstore.entity.Order;
import com.wiseowl.bookstore.entity.OrderItem;
import com.wiseowl.bookstore.repository.BookRepository;
import com.wiseowl.bookstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final BookRepository bookRepository; // To fetch book details

    public Order createOrder(Long userId, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(new Date().toString());

        double totalPrice = 0;
        for (OrderItem item : orderItems) {
            totalPrice += item.getTotalPrice();
        }
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }
}


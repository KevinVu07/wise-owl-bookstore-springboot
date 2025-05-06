package com.wiseowl.bookstore.service;

import com.wiseowl.bookstore.entity.*;
import com.wiseowl.bookstore.repository.BookRepository;
import com.wiseowl.bookstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final BookRepository bookRepository; // To fetch book details

    public Order createOrder(User user, List<CartItem> cartItems) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        populateOrderItemsFromCartItems(order, cartItems);

        return orderRepository.save(order);
    }

    public void populateOrderItemsFromCartItems(Order currentOrder, List<CartItem> cartItems) {
        List<OrderItems> orderItemsList = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            OrderItems orderItem = new OrderItems();
            orderItem.setBookId(cartItem.getBookId());
            orderItem.setBookName(cartItem.getBook().getName());
            orderItem.setBookImage(cartItem.getBook().getImage());
            orderItem.setBookPrice(cartItem.getBook().getSalePrice());
            orderItem.setOrderQty(cartItem.getBookQty());
            orderItem.setOrderItemTotal(cartItem.getBook().getSalePrice() * cartItem.getBookQty());

            orderItem.setOrder(currentOrder);
            orderItemsList.add(orderItem);
        }

        currentOrder.setOrderItems(orderItemsList);
    }
}


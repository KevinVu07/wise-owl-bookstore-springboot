package com.wiseowl.bookstore.service;

import com.wiseowl.bookstore.entity.*;
import com.wiseowl.bookstore.repository.BookRepository;
import com.wiseowl.bookstore.repository.OrderItemRepository;
import com.wiseowl.bookstore.repository.OrderRepository;
import com.wiseowl.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemsService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final BookRepository bookRepository; // To fetch book details

    private final CartService cartService;

    private final OrderService orderService;

    public Order getOrCreateCurrentOrderForUser(User user) {
        List<CartItem> cartItems = cartService.getCartItemsForUser(user.getId());

        // Try to fetch existing PENDING orders (latest first)
        List<Order> pendingOrders = orderRepository.findOrdersByUserAndOrderStatusOrderByOrderDateDesc(user, OrderStatus.PENDING);

        Order currentOrder;
        if (!pendingOrders.isEmpty()) {
            currentOrder = pendingOrders.get(0);  // use the latest one
        } else {
            // No pending order, create a new one
            currentOrder = new Order();
            currentOrder.setUser(user);
            currentOrder.setOrderStatus(OrderStatus.PENDING);
            currentOrder.setOrderDate(LocalDateTime.now());
            currentOrder.setOrderTotal(0.0);
            currentOrder = orderRepository.save(currentOrder);
        }

        // If cart has items, generate OrderItems from CartItems and attach to Order
        if (!cartItems.isEmpty()) {
            orderService.populateOrderItemsFromCartItems(currentOrder, cartItems);
        }

        return currentOrder;
    }


    public List<OrderItems> getCurrentOrderItemsForUser(User user) {
        Order currentOrder = getOrCreateCurrentOrderForUser(user);
        return currentOrder.getOrderItems();
    }
    }



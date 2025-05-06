package com.wiseowl.bookstore.repository;

import com.wiseowl.bookstore.entity.OrderStatus;
import com.wiseowl.bookstore.entity.Order;
import com.wiseowl.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUserAndOrderStatus(User user, OrderStatus orderStatus);

    List<Order> findOrdersByUserAndOrderStatusOrderByOrderDateDesc(User user, OrderStatus orderStatus);
}


package com.wiseowl.bookstore.repository;

import com.wiseowl.bookstore.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Find all order items by user ID
    List<OrderItem> findByUserId(Long userId);

    // Find all order items by order ID (if your table links items to a specific order ID)
    List<OrderItem> findByOrderId(Long orderId);

    // Optional — find all order items for a user on a specific date
    List<OrderItem> findByUserIdAndOrderDate(Long userId, String orderDate);
}


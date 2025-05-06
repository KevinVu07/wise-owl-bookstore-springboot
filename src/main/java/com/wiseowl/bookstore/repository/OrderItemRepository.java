package com.wiseowl.bookstore.repository;

import com.wiseowl.bookstore.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

    // Find all order items by order ID (if your table links items to a specific order ID)
    List<OrderItems> findByOrderId(Long orderId);

}


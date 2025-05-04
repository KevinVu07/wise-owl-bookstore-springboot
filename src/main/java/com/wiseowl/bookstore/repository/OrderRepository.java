package com.wiseowl.bookstore.repository;

import com.wiseowl.bookstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Custom finder method: find orders by userId
    List<Order> findByUserId(Long userId);

}


package com.wiseowl.bookstore.repository;

import com.wiseowl.bookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUserId(long userId);  // Get all cart items for a specific user
    Optional<CartItem> findByUserIdAndBookId(long userId, int bookId);  // Get cart items for a specific user and book
}

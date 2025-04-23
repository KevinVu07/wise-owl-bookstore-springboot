package com.wiseowl.bookstore.repository;

import com.wiseowl.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    // can add custom queries later if needed
}

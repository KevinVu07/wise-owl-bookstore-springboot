package com.wiseowl.bookstore.service;

import com.wiseowl.bookstore.model.Book;
import com.wiseowl.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Add more methods for save, update, delete if needed
}

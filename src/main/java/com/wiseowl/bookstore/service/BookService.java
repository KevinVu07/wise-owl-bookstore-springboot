package com.wiseowl.bookstore.service;

import com.wiseowl.bookstore.model.Book;
import com.wiseowl.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Add more methods for save, update, delete if needed
}

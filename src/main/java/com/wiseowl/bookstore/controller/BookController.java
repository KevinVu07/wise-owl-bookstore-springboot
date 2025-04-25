package com.wiseowl.bookstore.controller;


import com.wiseowl.bookstore.model.Book;
import com.wiseowl.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;

    @GetMapping("/books")
    public String listBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book-list";
    }
}

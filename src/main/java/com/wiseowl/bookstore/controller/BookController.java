package com.wiseowl.bookstore.controller;


import com.wiseowl.bookstore.entity.Book;
import com.wiseowl.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/book/{id}")
    public String showBookDetails(@PathVariable("id") int id, Model model) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            model.addAttribute("book", book);
            return "book-details";
        } else {
            return "redirect:/books"; // Redirect to the list if the book is not found
        }
    }
}

package com.example.booksrestservice.controller;

import com.example.booksrestservice.model.Book;
import com.example.booksrestservice.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService = new BookService();


    @GetMapping("/books") //for testing deserialization purposes
    public List<Book> findAllBooks(){
        return bookService.findBooks();
    }

}

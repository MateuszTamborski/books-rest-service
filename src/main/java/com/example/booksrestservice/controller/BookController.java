package com.example.booksrestservice.controller;

import com.example.booksrestservice.model.Book;
import com.example.booksrestservice.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService = new BookService();

    @GetMapping //for testing deserialization purposes
    public List<Book> findAllBooks(){
        return bookService.findAll();
    }

    @GetMapping(value = "/{isbn}")
    public Book findByIsbn(@PathVariable("isbn") String isbn){
        return bookService.findByIsbn(isbn);
    }

}

package com.example.booksrestservice.controller;

import com.example.booksrestservice.exceptions.ObjectNotFoundException;
import com.example.booksrestservice.model.AuthorRating;
import com.example.booksrestservice.model.Book;
import com.example.booksrestservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/categories/{category}")
    public List<Book> findByCategory(@PathVariable("category") String category){
        return bookService.findByCategory(category);
    }

    @GetMapping("/rating")
    public List<AuthorRating> findAuthorsRating(){
        return bookService.findAuthorsRating();
    }

    @GetMapping("/volume/{pages}")
    public Book findWithMorePages(@PathVariable("pages") Integer pages){
        return bookService.findWithMorePages(pages);
    }

    @GetMapping("/bestbooks/{pagesPerHour}/{avgHoursPerDay}")
    public List<Book> findByAvgRatingAndCanBeReadInMonth(@PathVariable Integer pagesPerHour, @PathVariable Integer avgHoursPerDay){
        return bookService.findByAvgRatingAndCanBeReadInMonth(pagesPerHour, avgHoursPerDay);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public void catchObjectNotFoundException(ObjectNotFoundException ex){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not found", ex);
    }
}

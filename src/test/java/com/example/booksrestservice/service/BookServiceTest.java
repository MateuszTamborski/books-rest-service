package com.example.booksrestservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() {
        assertEquals(40,bookService.findAll().size());
    }

    @Test
    void shouldFindBookByIsbn() {
        assertEquals("N1IiAQAAIAAJ", bookService.findByIsbn("N1IiAQAAIAAJ").getIsbn());
    }

    @Test
    void shouldFindBooksByCategory() {
        assertEquals(22, bookService.findByCategory("Computers").size());
    }

    @Test
    void shouldFindAuthorsRating() {
        assertEquals(15, bookService.findAuthorsRating().size());
    }

    @Test
    void shouldFindWithMorePages() {
        assertEquals(992, bookService.findWithMorePages(900).getPageCount());
    }

    @Test
    void shouldFindByAvgRatingAndCanBeReadInMonth() {
        assertEquals(1, bookService.findByAvgRatingAndCanBeReadInMonth(10, 1).size());
    }
}
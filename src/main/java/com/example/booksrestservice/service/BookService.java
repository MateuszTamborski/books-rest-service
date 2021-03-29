package com.example.booksrestservice.service;

import com.example.booksrestservice.BooksMapper;
import com.example.booksrestservice.model.Book;

import java.util.List;

public class BookService {
    private final BooksMapper booksMapper = new BooksMapper();

    public List<Book> findBooks(){
        List<Book> books = booksMapper.mapObjects();
        return books;
    }
}

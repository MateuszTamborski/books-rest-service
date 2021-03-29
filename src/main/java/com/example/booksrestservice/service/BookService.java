package com.example.booksrestservice.service;

import com.example.booksrestservice.BooksMapper;
import com.example.booksrestservice.model.Book;

import java.util.List;

public class BookService {

    private final BooksMapper booksMapper = new BooksMapper();

    public List<Book> findAll(){
        List<Book> books = booksMapper.mapObjects();
        return books;
    }

    public Book findByIsbn(String isbn){
        List<Book> books = booksMapper.mapObjects();
        return books.stream().filter(f->f.getIsbn().equals(isbn)).findFirst().get();
    }
}

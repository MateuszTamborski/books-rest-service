package com.example.booksrestservice.service;

import com.example.booksrestservice.BooksMapper;
import com.example.booksrestservice.exceptions.ObjectNotFoundException;
import com.example.booksrestservice.model.AuthorRating;
import com.example.booksrestservice.model.Book;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {

    @Autowired
    private final List<Book> listOfBooks = new BooksMapper().mapObjects();

    public List<Book> findAll(){
        return listOfBooks;
    }

    public Book findByIsbn(String isbn){
        return listOfBooks.stream()
                .filter(book->book.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(ObjectNotFoundException::new);
    }

    public List<Book> findByCategory(String category){
        return listOfBooks.stream()
                .filter(book->book.getCategories().contains(category))
                .collect(Collectors.toList());
    }

    public List<AuthorRating> findAuthorsRating(){
        List<AuthorRating> authorRatingsList = new ArrayList<>();
        List<String> authors = listOfBooks.stream()
                                          .flatMap(a->a.getAuthors().stream())
                                          .distinct()
                                          .collect(Collectors.toList());

        for (String author: authors) {
            Double avgRating = listOfBooks.stream().filter(book -> book.getAuthors().contains(author))
                    .filter(book -> {
                        Double avg = book.getAverageRating();
                        return avg != null;
                    })
                    .mapToDouble(Book::getAverageRating)
                    .average()
                    .orElse(0.0);

            if (avgRating != 0.0) {
                AuthorRating authorRating = new AuthorRating(author, avgRating);
                authorRatingsList.add(authorRating);
            }
        }
        return authorRatingsList.stream()
                .sorted(Comparator.comparing(AuthorRating::getAverageRating).reversed())
                .collect(Collectors.toList());
    }
}

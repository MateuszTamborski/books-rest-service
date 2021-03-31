package com.example.booksrestservice;

import com.example.booksrestservice.model.Book;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BooksMapperTest {
    private final BooksMapper booksMapper = new BooksMapper();

    @Test
    void shouldReadFromTestJsonAndReturnJNode() {
        JsonNode readObject = booksMapper.readFromJson("test.json");
        assertNotNull(readObject);
    }

    @Test
    void shouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            booksMapper.readFromJson("wrong.json");
        });
    }

    @Test
    void shouldMapTwoBookObjects() {
        assertEquals(2, booksMapper.mapObjects("test.json").size());
    }

    @Test
    void shouldMapIsbns(){
        List<Book> books = booksMapper.mapObjects("test.json");
        assertEquals("9781492056089", books.get(0).getIsbn());
        assertEquals("J0C7DwAAQBAJ", books.get(1).getIsbn());
    }

    @Test
    void shouldMapPageCounts(){
        List<Book> books = booksMapper.mapObjects("test.json");
        assertEquals(450, books.get(0).getPageCount());
        assertEquals(606, books.get(1).getPageCount());
    }

    @Test
    void shouldMapThumbnails(){
        List<Book> books = booksMapper.mapObjects("test.json");
        assertEquals("http://books.google.com/books/content?id=Q3_QDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api", books.get(0).getThumbnailUrl());
        assertEquals("http://books.google.com/books/content?id=J0C7DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api", books.get(1).getThumbnailUrl());
    }

    @Test
    void shouldMapAverageRatings(){
        List<Book> books = booksMapper.mapObjects("test.json");
        assertEquals(3.0, books.get(0).getAverageRating());
        assertEquals(5.0, books.get(1).getAverageRating());
    }

    @Test
    void shouldMapDatesToUnixTimestamp(){
        List<Book> books = booksMapper.mapObjects("test.json");
        assertEquals( 1581379200000L, books.get(0).getPublishedDate());
        assertEquals(1572480000000L, books.get(1).getPublishedDate());
    }
}
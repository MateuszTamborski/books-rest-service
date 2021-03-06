package com.example.booksrestservice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
class EndpointTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    void findAllBooksShouldReturnAllBooks() {
        when().get("/books")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", is(40));
    }

    @Test
    void findByIsbnShouldReturnBook() {
        when().get("/books/N1IiAQAAIAAJ")
                .then()
                .statusCode(200)
                .assertThat()
                .body("title", equalTo("Java"));
    }

    @Test
    void findByIsbnShouldReturn404(){
        when().get("/books/wrongBookIsbn")
                .then()
                .statusCode(404);
    }

    @Test
    void findByCategoryShouldReturnAllBooksFromCategory() {
        when().get("/books/categories/Computers")
                .then()
                .statusCode(200)
                .assertThat()
                .body("", hasSize(22));
    }

    @Test
    void findAuthorsRatingShouldReturnRatings() {
        when().get("/books/rating")
                .then()
                .statusCode(200)
                .assertThat()
                .body("[0].author", equalTo("Jain Pravin"));
    }

    @Test
    void findWithMorePages() {
        when().get("/books/volume/991")
                .then()
                .statusCode(200)
                .assertThat()
                .body("pageCount", equalTo(992));
    }

    @Test
    void findByAvgRatingAndCanBeReadInMonth() {
        when().get("/books/bestbooks/10/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("[0].pageCount", equalTo(192));
    }
}
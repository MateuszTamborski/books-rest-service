package com.example.booksrestservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    private String isbn;
    private String title;
    private String subtitle;
    private String publisher;
    private Long publishedDate;
    private String description;
    private Integer pageCount;
    private String thumbnailUrl;
    private String language;
    private String previewLink;
    private Double averageRating;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ArrayList<String> authors;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ArrayList<String> categories;

}

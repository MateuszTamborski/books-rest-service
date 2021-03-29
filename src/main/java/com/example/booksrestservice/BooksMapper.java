package com.example.booksrestservice;

import com.example.booksrestservice.model.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class BooksMapper {

    public JsonNode readFromJson(){
        try(InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("books.json")){
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            return jsonNode.get("items");
        }
        catch (IOException e){
            return null;
        }
    }

    public List<Book> mapObjects(){
        List<Book> books = new ArrayList<>();
        JsonNode items = readFromJson();
        items.forEach(x -> {
            JsonNode volumeInfo = x.get("volumeInfo");
            books.add(Book.builder()
                    .isbn(mapIsbn(x))
                    .title(mapString(volumeInfo.get("title")))
                    .subtitle(mapString(volumeInfo.get("subtitle")))
                    .publisher(mapString(volumeInfo.get("publisher")))
                    .publishedDate(mapDateToEpoch(volumeInfo.get("publishedDate")))
                    .description(mapString(volumeInfo.get("description")))
                    .pageCount(mapPageCount(volumeInfo.get("pageCount")))
                    .thumbnailUrl(mapThumbnail(volumeInfo))
                    .language(mapString(volumeInfo.get("language")))
                    .previewLink(mapString(volumeInfo.get("previewLink")))
                    .averageRating(mapAverageRating(volumeInfo.get("averageRating")))
                    .authors(mapStringArray(volumeInfo.get("authors")))
                    .categories(mapStringArray(volumeInfo.get("categories")))
                    .build());
        });
        return books;
    }

    private String mapIsbn(JsonNode jsonNode){
        JsonNode volumeInfo = jsonNode.get("volumeInfo");
        if(volumeInfo != null) {
            JsonNode industryIdentifiers = volumeInfo.get("industryIdentifiers");
            if(industryIdentifiers != null) {
                for (JsonNode jNode : industryIdentifiers) {
                    if ("ISBN_13".equals(mapString(jNode.get("type")))) {
                        return mapString(jNode.get("identifier"));
                    }
                }
            }
        }
        return jsonNode.get("id").asText();
    }

    private String mapThumbnail(JsonNode jsonNode){
        JsonNode imageLinks = jsonNode.get("imageLinks");
        if(imageLinks != null){
            return mapString(imageLinks.get("thumbnail"));
        }
        return null;
    }

    private Integer mapPageCount(JsonNode jsonNode){
        if(jsonNode == null){
            return null;
        }
        return jsonNode.asInt();
    }

    private Double mapAverageRating(JsonNode jsonNode){
        if(jsonNode == null){
            return null;
        }
        return jsonNode.asDouble();
    }

    private Long mapDateToEpoch(JsonNode jsonNode){
        try {
            return LocalDate.parse(jsonNode.asText()).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        } catch (DateTimeParseException | NullPointerException e)
        {
            // fall through, date is not in dd-mm-yyyy format
        }
        try {
            DateTimeFormatter format = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy")
                    .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                    .toFormatter();
            return LocalDate.parse(jsonNode.asText(),format).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        } catch (DateTimeParseException | NullPointerException e){
            return null;
        }
    }

    private ArrayList<String> mapStringArray(JsonNode jsonNode){
        ArrayList<String> arrayList = new ArrayList<>();
        if(jsonNode != null && jsonNode.isArray()){
            for (JsonNode arrayElement : jsonNode){
                arrayList.add(arrayElement.asText());
            }
            return arrayList;
        }
        return new ArrayList<>();
    }

    private String mapString(JsonNode jsonNode){
        if(jsonNode == null){
            return null;
        }
        return jsonNode.asText();
    }
}

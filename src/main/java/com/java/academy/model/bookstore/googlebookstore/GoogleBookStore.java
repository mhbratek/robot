package com.java.academy.model.bookstore.googlebookstore;

import com.google.gson.Gson;
import com.java.academy.model.Book;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author bratek
 * @since 24.08.17
 */
public class GoogleBookStore {

    public static final String URL_TO_GET_BOOKS = "https://www.googleapis.com/books/v1/volumes?&q=-&fields=totalItems," +
            "items(volumeInfo/title,volumeInfo/subtitle,volumeInfo/description,volumeInfo/infoLink,saleInfo/retailPrice," +
            "saleInfo/listPrice/amount,volumeInfo/authors,volumeInfo/imageLinks/smallThumbnail)&maxResults=40";

    public List<Book> collectBooksFromGoogle() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(URL_TO_GET_BOOKS, HttpMethod.GET, entity, String.class);


        GoogleBook googleBooks = new Gson().fromJson(response.getBody(), GoogleBook.class);


        return BookMapper.mapFromGoogleBookStore(googleBooks);
    }
}

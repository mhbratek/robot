package googlebookstore;

import com.google.gson.Gson;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
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

    private static final String BOOKSTORE_NAME = "GooglePlay";
    private static final String BOOKSTORE_URL = "https://play.google.com/store/books?hl=en";

    private static final String URL_TO_GET_BOOKS = "https://www.googleapis.com/books/v1/volumes?&q=-&fields=totalItems," +
            "items(volumeInfo/title,volumeInfo/subtitle,volumeInfo/description,volumeInfo/infoLink,saleInfo/retailPrice," +
            "saleInfo/listPrice/amount,volumeInfo/authors,volumeInfo/imageLinks/smallThumbnail)&maxResults=40&startIndex=";

    public List<Book> collectBooksFromGoogle() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        List<Book> books = new ArrayList<>();

        books = collect440Books(restTemplate, headers, books);

        return books;
    }

    private List<Book> collect440Books(RestTemplate restTemplate, HttpHeaders headers, List<Book> books) {
        Bookstore bookstore = new Bookstore(BOOKSTORE_NAME, BOOKSTORE_URL);

        for(int i = 0; i <= 400; i = i + 40) {
            HttpEntity<?> entity = new HttpEntity<>(headers);

            HttpEntity<String> response = restTemplate.exchange(URL_TO_GET_BOOKS + i, HttpMethod.GET, entity, String.class);

            GoogleBook googleBooks = new Gson().fromJson(response.getBody(), GoogleBook.class);

            books.addAll(BookMapper.mapFromGoogleBookStore(googleBooks, bookstore));
        }
        return books;
    }
}

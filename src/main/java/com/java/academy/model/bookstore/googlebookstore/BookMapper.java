package com.java.academy.model.bookstore.googlebookstore;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bratek
 * @since 24.08.17
 */
public class BookMapper {
    public static List<Book> mapFromGoogleBookStore(GoogleBook googleBook) {
        List<Book> books = new ArrayList<>();

        for (Item book : googleBook.getItems()) {
            Book bookToAdd = new Book();
            bookToAdd.setTitle(book.getVolumeInfo().getTitle());
            bookToAdd.setAuthor(book.getVolumeInfo().getAuthors().get(0));
//            bookToAdd.setBookstore(new Bookstore("GOOGLE", "www.google.pl"));
            bookToAdd.setCategory("CAT");
            bookToAdd.setImgUrl(book.getVolumeInfo().getImageLinks().getSmallThumbnail());
            bookToAdd.setPrice(new BigDecimal(20));
            bookToAdd.setUrl("www.www.pl");
            books.add(bookToAdd);
        }


        return books;
    }
}

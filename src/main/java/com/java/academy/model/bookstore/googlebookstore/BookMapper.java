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

        Bookstore bookstore =  new Bookstore("GooglePlay", "https://play.google.com/store/books?hl=en");


        for (Item book : googleBook.getItems()) {
            Book bookToAdd = new Book(

            );

            bookToAdd.setTitle(book.getVolumeInfo().getTitle());

            if (book.getVolumeInfo().getAuthors() != null){
                bookToAdd.setAuthor(book.getVolumeInfo().getAuthors().get(0));
            } else {
                bookToAdd.setAuthor("Unknown");
            }

            bookToAdd.setBookstore(bookstore);

            if (book.getSaleInfo() != null) {
                bookToAdd.setPrice(new BigDecimal(book.getSaleInfo().getListPrice().getAmount()));
                bookToAdd.setPromoDetails(String.valueOf((int)(((book.getSaleInfo().getListPrice().getAmount() -
                        book.getSaleInfo().getRetailPrice().getAmount()) /
                book.getSaleInfo().getListPrice().getAmount()) * 100)) + "%");
            } else {
                bookToAdd.setPrice(new BigDecimal(0));
            }
            bookToAdd.setUrl(book.getVolumeInfo().getInfoLink());

            books.add(bookToAdd);
        }


        return books;
    }
}

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

    private static final String BOOKSTORE_NAME = "GooglePlay";
    private static final String BOOKSTORE_URL = "https://play.google.com/store/books?hl=en";

    public static List<Book> mapFromGoogleBookStore(GoogleBook googleBook) {
        List<Book> books = new ArrayList<>();

        Bookstore bookstore =  new Bookstore(BOOKSTORE_NAME, BOOKSTORE_URL);

        for (Item book : googleBook.getItems()) {
            Book bookToAdd = new Book();

            bookToAdd.setTitle(book.getTitle());

            setAuthors(book, bookToAdd);

            bookToAdd.setBookstore(bookstore);

            setPrice(book, bookToAdd);

            bookToAdd.setUrl(book.getLink());

            setUrl(book, bookToAdd);

            setCategory(book, bookToAdd);

            books.add(bookToAdd);
        }


        return books;
    }

    private static void setCategory(Item book, Book bookToAdd) {
        if (ifBookHasCateogry(book)) {
            bookToAdd.setCategory(book.getCategory());
        } else {
            bookToAdd.setCategory("Book");
        }
    }

    private static void setUrl(Item book, Book bookToAdd) {
        if(ifBookHasImage(book)) {
            bookToAdd.setImgUrl(book.getImageLink());
        }
    }

    private static void setPrice(Item book, Book bookToAdd) {
        if (ifBookHasPrice(book)) {
            bookToAdd.setPrice(new BigDecimal(book.getPrice()));
            bookToAdd.setPromoDetails(countPromoDetails(book));
        } else {
            bookToAdd.setPrice(new BigDecimal(0));
        }
    }

    private static void setAuthors(Item book, Book bookToAdd) {
        if (isAuthorExists(book)){
            bookToAdd.setAuthor(book.getAuthors());
        } else {
            bookToAdd.setAuthor("Unknown");
        }
    }

    private static boolean ifBookHasCateogry(Item book) {
        return book.getVolumeInfo().getMainCategory() != null;
    }

    private static boolean ifBookHasImage(Item book) {
        return book.getVolumeInfo().getImageLinks() != null;
    }

    private static boolean ifBookHasPrice(Item book) {
        return book.getSaleInfo() != null;
    }

    private static String countPromoDetails(Item book) {
        return String.valueOf((int)(((book.getSaleInfo().getListPrice().getAmount() -
                book.getSaleInfo().getRetailPrice().getAmount()) /
        book.getSaleInfo().getListPrice().getAmount()) * 100)) + "%";
    }

    private static boolean isAuthorExists(Item book) {
        return book.getVolumeInfo().getAuthors() != null;
    }
}

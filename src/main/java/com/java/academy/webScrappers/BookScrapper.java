package com.java.academy.webScrappers;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import org.jsoup.nodes.Document;

import java.math.BigDecimal;
import java.util.List;

public interface BookScrapper {

    void initBookStore();
    Document provideShopConnection(String linkToConnect);
    List<Book> collectBooksFromSinglePage(Document doc);
    String getBookAuthor();
    String getImageUrl();
    String getBookCategory();
    String getBookTitle();
    String getBookLink();
    Double getBookPrice();
    String getDiscount();
    Bookstore getBookStore();

    default Book setupBook() {
        Book singleBook = new Book(getBookTitle(),
                getBookAuthor(),
                getBookCategory(),
                getDiscount(),
                new BigDecimal(getBookPrice()),
                getBookStore());

        singleBook.setUrl(getBookLink());
        singleBook.setImgUrl(getImageUrl());
        return singleBook;
    }
}


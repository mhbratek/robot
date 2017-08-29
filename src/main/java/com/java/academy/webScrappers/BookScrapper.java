package com.java.academy.webScrappers;

import com.java.academy.model.Book;
import org.jsoup.nodes.Document;

import java.util.List;

public interface BookScrapper {

    List<Book> collectBooksFromSinglePage(Document doc);
    String getBookAuthor();
    String getImageUrl();
    String getBookCategory();
    String getBookTitle();
    String getBookLink();
    Double getBookPrice();
    String getDiscount();
}


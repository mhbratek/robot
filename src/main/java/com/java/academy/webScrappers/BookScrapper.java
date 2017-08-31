package com.java.academy.webScrappers;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;
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
    Bookstore getBookStore();

    default String getPropertyByClassName(String className, Element currentPage) {
        return currentPage.getElementsByClass(className).text();
    }

    default Document provideShopConnection(String linkToConnect, DocumentLoader loader) {
        Document shopConnection = null;
        try {
            shopConnection = loader.loadHTMLDocument(linkToConnect);
        } catch (IOException e) {
            System.err.println("wrong url cannot connect "+ linkToConnect);
        }
        return shopConnection;
    }

    default Bookstore initBookStore(String shopName, String url) {
        Bookstore bookstore = new Bookstore();
        bookstore.setName(shopName);
        bookstore.setUrl(url);
        return bookstore;
    }

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


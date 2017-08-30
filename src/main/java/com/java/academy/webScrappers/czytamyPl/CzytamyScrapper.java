package com.java.academy.webScrappers.czytamyPl;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import com.java.academy.webScrappers.BookScrapper;
import com.java.academy.webScrappers.DocumentLoader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CzytamyScrapper implements BookScrapper {

    private final static String HOST = "http://czytam.pl";
    private final int FIRST_ELEMENT = 0;

    private String url;
    private Bookstore bookstore;
    private Element currentBook;
    private DocumentLoader loader;

    public CzytamyScrapper(DocumentLoader loader) {
        this.loader = loader;
        initBookStore();
    }

    public List<Book> collectBooksFromCzytamyPl() {
        List<Book> books = new ArrayList<>();

        for (int page = 0; page < 10; page++) {
            url = "http://czytam.pl/ksiazki-promocje," + (page + 1) + ".html";
            Document matrasBooks = provideShopConnection(url);
            books.addAll(collectBooksFromSinglePage(matrasBooks));
        }

        return books;
    }

    @Override
    public void initBookStore() {
        bookstore = new Bookstore();
        bookstore.setName("Czytamy");
        bookstore.setUrl(HOST);
    }

    @Override
    public Bookstore getBookStore() {
        return this.bookstore;
    }

    @Override
    public Document provideShopConnection(String linkToConnect) {
        Document shopConnection = null;
        try {
            shopConnection = loader.loadHTMLDocument(linkToConnect);
        } catch (IOException e) {
            System.err.println("wrong url " + linkToConnect);
        }
        return shopConnection;
    }

    @Override
    public List<Book> collectBooksFromSinglePage(Document doc) {
        Elements prod = doc.getElementsByClass("product");
        List<Book> singlePageBooks = new ArrayList<>();

        prod.forEach(product -> {
            currentBook = product;
            singlePageBooks.add(setupBook());
        });

        return singlePageBooks;
    }

    @Override
    public String getBookAuthor() {
        Elements author = currentBook.getElementsByClass("product-author");
        return author.text();
    }

    @Override
    public String getImageUrl() {
        Elements image = currentBook.getElementsByClass("has-tip [tip-left] th [radius]");
        return image.get(FIRST_ELEMENT).getElementsByTag("img").attr("src");
    }

    @Override
    public String getBookCategory() {
        Document details = provideShopConnection(getBookLink());
        Elements category = details.getElementsByClass("headline-azure");
        return category.text().isEmpty() ? "nieznany" : category.text().replaceAll(getBookAuthor(), "").trim();
    }

    @Override
    public String getBookTitle() {
        Elements title = currentBook.getElementsByClass("product-title");
        return title.text();
    }

    @Override
    public String getBookLink() {
        Elements bookLink = currentBook.getElementsByClass("product-title");
        return HOST + bookLink.get(FIRST_ELEMENT)
                .getElementsByTag("a")
                .attr("href");
    }

    @Override
    public Double getBookPrice() {
        Elements price = currentBook.getElementsByClass("strike");
        return Double.parseDouble(price.text()
                .replaceAll(",", ".")
                .replaceAll("[A-ż]+", ""));
    }

    @Override
    public String getDiscount() {
        Elements discount = currentBook.getElementsByClass("icon_rabat");
        return discount.text();
    }
}



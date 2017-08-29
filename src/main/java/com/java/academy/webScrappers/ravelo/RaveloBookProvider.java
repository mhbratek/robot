package com.java.academy.webScrappers.ravelo;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RaveloBookProvider {

    private final static String HOST = "https://www.ravelo.pl";

    private RaveloScrapper raveloScrapper;
    private Bookstore bookstore;
    private String url = "https://www.ravelo.pl/outlet";

    public List<Book> getBooksFromRavelo() {
        List<Book> raveloBooks = new ArrayList<>();
        try {
            Document raveloPromoBase = Jsoup.connect(url).get();
            initializeBookStore();

            collectLinksToAllBooksCategory(raveloPromoBase).forEach(link -> {
                raveloScrapper = new RaveloScrapper(link, bookstore);
                raveloBooks.addAll(raveloScrapper.prepareBookPackage());

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return raveloBooks;
    }

    void initializeBookStore() {
        this.bookstore = new Bookstore();
        this.bookstore.setName("Ravelo");
        this.bookstore.setUrl(HOST);
    }

    public Bookstore getBookStore() {
        return bookstore;
    }

    public List<String> collectLinksToAllBooksCategory(Document raveloPromoBase) {
        Elements categories = raveloPromoBase.getElementsByClass("row showcase showcase6x1 m-books4 ");
        List<String> categoryLinks = new ArrayList<>();

        categories.forEach(element -> {
            Elements links = element.getElementsByClass("see-more");

            links.forEach(link -> {
                categoryLinks.add(
                        (HOST + link
                                .getElementsByTag("a")
                                .attr("href")
                                .replaceAll(HOST, "")));
            });
        });

        //last link is for toys so no need to check
        categoryLinks.remove(categoryLinks.size() - 1);
        //first is best offers so also no need to check
        categoryLinks.remove(0);

        return categoryLinks;
    }
}



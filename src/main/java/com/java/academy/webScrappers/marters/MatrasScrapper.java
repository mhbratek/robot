package com.java.academy.webScrappers.marters;

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

public class MatrasScrapper implements BookScrapper{

    private final static String HOST = "http://www.matras.pl";
    private final int FIRST_ELEMENT = 0;

    private String url;
    private Bookstore bookstore;
    private Element currentBook;
    private DocumentLoader loader;

    public MatrasScrapper(DocumentLoader loader) {
        this.loader = loader;
        initBookStore();
    }

    public List<Book> collectBooksFromMatras() {
        List<Book> books = new ArrayList<>();

        for (int page = 1; page < 2; page++) {
            url = "http://www.matras.pl/ksiazki/tania-ksiazka,k,54?p=" + page;
            Document matrasBooks = provideShopConnection(url);
            books.addAll(collectBooksFromSinglePage(matrasBooks));
        }

        return books;
    }

    @Override
    public void initBookStore() {
        bookstore = new Bookstore();
        bookstore.setName("Matras");
        bookstore.setUrl(HOST);
    }
    @Override
    public Document provideShopConnection(String linkToConnect)  {
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
        Elements prod = doc.getElementsByClass("s-item eqh");
        List<Book> singlePageBooks = new ArrayList<>();

        prod.forEach(product -> {
            currentBook = product;
            singlePageBooks.add(setupBook());
        });

        return singlePageBooks;
    }

    @Override
    public Bookstore getBookStore() {
        return this.bookstore;
    }

    @Override
    public String getBookAuthor() {
        Elements author = currentBook.getElementsByClass("item-author");
        return author.text();
    }

    @Override
    public String getImageUrl() {
        Elements image = currentBook.getElementsByClass("image");
        return image.get(FIRST_ELEMENT).getElementsByTag("img").attr("data-original");
    }

    @Override
    public String getBookCategory() {
        Document details = provideShopConnection(getBookLink());
        Elements category = details.getElementsByClass("m-list");
        return category.text().isEmpty() ? "nieznany" : category.text()
                                                    .substring(category.text()
                                                    .lastIndexOf("Książki") + "Książki".length()).trim();
            }

    @Override
    public String getBookTitle() {
        Elements title = currentBook.getElementsByClass("item-title");
        return title.text();
    }

    @Override
    public String getBookLink() {
        Elements bookLink = currentBook.getElementsByClass("right-side");
        return bookLink.get(FIRST_ELEMENT)
                .getElementsByTag("a")
                .attr("href");
    }

    @Override
    public Double getBookPrice() {
        Elements price = currentBook.getElementsByClass("item-price");
        return Double.parseDouble(price.text()
                .replaceAll(",", ".")
                .replaceAll("[a-ż]+", ""));
    }

    @Override
    public String getDiscount() {
        Elements discount = currentBook.getElementsByClass("line discount-line");
        return discount.text().replaceAll("[A-ż]+", "-");
    }
}

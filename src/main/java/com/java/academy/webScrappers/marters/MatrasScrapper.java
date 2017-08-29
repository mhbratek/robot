package com.java.academy.webScrappers.marters;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import com.java.academy.webScrappers.BookScrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MatrasScrapper implements BookScrapper{

    private String url;

    public static void main(String[] args) {
        MatrasScrapper matrasScrapper = new MatrasScrapper();
        matrasScrapper.collectBooksFromMatras();
    }

    private final static String HOST = "http://www.matras.pl";
    private final int FIRST_ELEMENT = 0;

    private Bookstore bookstore;
    private Element tempBook;

    public List<Book> collectBooksFromMatras() {
        List<Book> books = new ArrayList<>();
        initBookStore();

        try {
            for (int page = 1; page < 10; page++) {
                url = "http://www.matras.pl/ksiazki/tania-ksiazka,k,54?p=" + page;
                Document matrasBooks = Jsoup.connect(url).get();

                books.addAll(collectBooksFromSinglePage(matrasBooks));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    Bookstore getBookStore() {
        return this.bookstore;
    }

    public void initBookStore() {
        bookstore = new Bookstore();
        bookstore.setName("Matras");
        bookstore.setUrl(HOST);
    }

    @Override
    public List<Book> collectBooksFromSinglePage(Document doc) {
        Elements prod = doc.getElementsByClass("s-item eqh");
        List<Book> singlePageBooks = new ArrayList<>();

        prod.forEach(product -> {
            tempBook = product;
            Book marterBook = new Book(getBookTitle(),
                    getBookAuthor(),
                    getBookCategory(),
                    getDiscount(),
                    new BigDecimal(getBookPrice()),
                    bookstore);

            marterBook.setUrl(getBookLink());
            marterBook.setImgUrl(getImageUrl());
            singlePageBooks.add(marterBook);
        });

        return singlePageBooks;
    }

    @Override
    public String getBookAuthor() {
        Elements author = tempBook.getElementsByClass("item-author");
        return author.text();
    }

    @Override
    public String getImageUrl() {
        Elements image = tempBook.getElementsByClass("image");
        return image.get(FIRST_ELEMENT).getElementsByTag("img").attr("data-original");
    }

    @Override
    public String getBookCategory() {
        return "tania-książka";
    }

    @Override
    public String getBookTitle() {
        Elements title = tempBook.getElementsByClass("item-title");
        return title.text();
    }

    @Override
    public String getBookLink() {
        Elements bookLink = tempBook.getElementsByClass("right-side");
        return bookLink.get(FIRST_ELEMENT)
                .getElementsByTag("a")
                .attr("href");
    }

    @Override
    public Double getBookPrice() {
        Elements price = tempBook.getElementsByClass("item-price");
        return Double.parseDouble(price.text()
                .replaceAll(",", ".")
                .replaceAll("[a-ż]+", ""));
    }

    @Override
    public String getDiscount() {
        Elements discount = tempBook.getElementsByClass("line discount-line");
        return discount.text().replaceAll("[A-ż]+", "-");
    }
}

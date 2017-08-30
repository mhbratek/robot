package com.java.academy.webScrappers.gandalf;

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

public class GandalfScrapper  implements BookScrapper {

    private static final int FIRST_ELEMENT = 0;
    private final static String HOST = "http://www.gandalf.com.pl";

    private Element currentBook;
    private String url ="http://www.gandalf.com.pl/promocje/";
    private Bookstore bookstore;
    private DocumentLoader loader;

    public GandalfScrapper(DocumentLoader loader) {
        this.loader = loader;
        initBookStore();
    }

    public List<Book> collectBooksFromGandalfBookstore() {
        List<Book> books = new ArrayList<>();


            Document doc = provideShopConnection(url);
//            Elements pages = doc.getElementsByClass("paging_number_link");
//            int pagesNumber = Integer.parseInt(pages.get(pages.size()-1).text());
//            for (int page = 1; page < pagesNumber-1; page++) { //to long to better performance just 5 pages
            for (int page = 0; page < 10; page++) {
                books.addAll(collectBooksFromSinglePage(doc));

                url = "http://www.gandalf.com.pl/promocje/" + (page + 1) + "/";
                doc = provideShopConnection(url);
            }

        return books;
    }
    @Override
    public void initBookStore() {
        bookstore = new Bookstore();
        bookstore.setName("Gandalf");
        bookstore.setUrl(HOST);
    }

    @Override
    public Document provideShopConnection(String linkToConnect)  {
        Document shopConnection = null;
        try {
             shopConnection = loader.loadHTMLDocument(linkToConnect);
        } catch (IOException e) {
            System.err.println("wrong url cannot connect "+ linkToConnect);
        }
        return shopConnection;
    }

    @Override
    public Bookstore getBookStore() {
        return this.bookstore;
    }

    @Override
    public List<Book> collectBooksFromSinglePage(Document doc) {
        Elements prod = doc.getElementsByClass("prod");
        List<Book> singlePageBooks = new ArrayList<>();

        prod.forEach(product -> {
            currentBook = product;
            singlePageBooks.add(setupBook());
        });
        return singlePageBooks;
    }

    @Override
    public String getBookAuthor() {
        Elements author = currentBook.getElementsByClass("h3");
        return author.text();
    }

    @Override
    public String getImageUrl() {
        Elements image = currentBook.getElementsByTag("img");
        return (HOST + image.attr("src"));
    }

    @Override
    public String getBookCategory() {
        Document productDetails = provideShopConnection(getBookLink());
        Elements genre = productDetails.getElementsByClass("product_categories");
        return genre.text().substring(genre.text().lastIndexOf(":") + 1).trim();
    }

    @Override
    public String getBookTitle() {
        Elements title = currentBook.getElementsByClass("h2");
        return title.text();
    }

    @Override
    public String getBookLink() {
        Elements links = currentBook.getElementsByClass("h2").get(FIRST_ELEMENT).getElementsByTag("a");
        return (HOST + links.attr("href"));
    }

    @Override
    public Double getBookPrice() {
        Elements price = currentBook.getElementsByClass("new_price");
        return Double.parseDouble(price.text()
                .replaceAll("[a-ż]+", "").replace(',', '.'));
    }

    @Override
    public String getDiscount() {
        Elements discount = currentBook.getElementsByClass("price_dis");
        return discount.text().replaceAll("[a-z]+", "");
    }
}




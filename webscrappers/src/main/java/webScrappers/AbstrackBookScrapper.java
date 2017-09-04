package webScrappers;

import com.java.academy.model.Bookstore;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


public abstract class AbstrackBookScrapper implements BookScrapper {

    protected Bookstore bookstore;
    protected String authorClassName;
    protected String discountClassName;
    protected String titleClassName;

    protected Bookstore initBookStore(String shopName, String url) {
        Bookstore bookstore = new Bookstore();
        bookstore.setName(shopName);
        bookstore.setUrl(url);
        return bookstore;
    }

    protected Document provideShopConnection(String linkToConnect, DocumentLoader loader) {
        Document shopConnection = null;
        try {
            shopConnection = loader.loadHTMLDocument(linkToConnect);
        } catch (IOException e) {
            System.err.println("wrong url cannot connect "+ linkToConnect);
        }
        return shopConnection;
    }

    @Override
    public String getBookTitle(Element product) {
        return product.getElementsByClass(titleClassName).text();
    }

    @Override
    public String getBookAuthor(Element product) {
        return product.getElementsByClass(authorClassName).text();
    }

    @Override
    public String getDiscount(Element product) {
        return product.getElementsByClass(discountClassName).text();
    }

    @Override
    public Bookstore getBookStore() {
        return bookstore;
    }

}



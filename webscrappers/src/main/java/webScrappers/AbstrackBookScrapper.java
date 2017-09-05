package webScrappers;

import logger.RLog;
import com.java.academy.model.Bookstore;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


public abstract class AbstrackBookScrapper implements BookScrapper {

    protected final int FIRST_ELEMENT = 0;

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
            RLog.error(RLog.getLogger(getClass()), e.getMessage());
        }
        return shopConnection;
    }

    protected void initializeDataToScrap(String bookstoreName, String bookstroeUrl, String author,
                                         String discount, String title) {

        bookstore = initBookStore(bookstoreName, bookstroeUrl);
        authorClassName = author;
        discountClassName = discount;
        titleClassName = title;
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



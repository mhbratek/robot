package com.java.academy.webScrappers.ravelo;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import com.java.academy.webScrappers.BookScrapper;
import com.java.academy.webScrappers.DocumentLoader;
import com.java.academy.webScrappers.JSOUPLoader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RaveloScrapper implements BookScrapper{
    private static final int FIRST_ELEMENT = 0;
    private final static String HOST = "https://www.ravelo.pl";

    private String url = "https://www.ravelo.pl/outlet";
    private Document shopConnection;
    private Bookstore bookstore;
    private Element tempBook;
    private String bookCategory;
    private DocumentLoader loader;

    public static void main(String[] args) {
        RaveloScrapper raveloScrapper = new RaveloScrapper(new JSOUPLoader());
        raveloScrapper.getBooksFromRavelo();
    }

    RaveloScrapper(DocumentLoader documentLoader) {
        this.loader = documentLoader;
        initBookStore();
    }

    public List<Book> getBooksFromRavelo() {
        List<Book> raveloBooks = new ArrayList<>();
            Document raveloPromoBase = provideShopConnection(url);

            collectLinksToAllBooksCategory(raveloPromoBase)
                    .forEach( link -> raveloBooks.addAll(prepareBookPackage(link)));

        return raveloBooks;
    }

    List<String> collectLinksToAllBooksCategory(Document raveloPromoBase) {
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

    List<Book> prepareBookPackage(String link) {
        List<Book> booksByGenre = new ArrayList<>();
        String nextPageLink;

        this.shopConnection =  provideShopConnection(link);

            for (int page = 0; page < getTotalNumberOfPages(); page++) {
                booksByGenre.addAll(collectBooksFromSinglePage(shopConnection));
                nextPageLink = link + "&p=" + (page+1);
                provideShopConnection(nextPageLink);
            }
        return booksByGenre;
    }

    @Override
    public void initBookStore() {
        this.bookstore = new Bookstore();
        this.bookstore.setName("Ravelo");
        this.bookstore.setUrl(HOST);
    }

    @Override
    public Document provideShopConnection(String linkToConnect)  {
        Document connection = null;
        try {
            connection = loader.loadHTMLDocument(linkToConnect);
        } catch (IOException e) {
            System.out.println("wrong url + " + linkToConnect);
        }
        return connection;
    }

    int getTotalNumberOfPages() {
        String[] pages = shopConnection.getElementsByClass("pagination")
                .text()
                .replaceAll("[a-ż]+", "")
                .split(" ");
        return Integer.parseInt(pages[pages.length-1]);
    }

    @Override
    public List<Book> collectBooksFromSinglePage(Document connection) {
        this.shopConnection = connection;
        Elements bookContainer = connection.getElementsByClass("row productBox ");
        List<Book> singlePage = new ArrayList<>();

        bookContainer.forEach(book -> {
            tempBook = book;
            singlePage.add(setupBook());
        });

        return singlePage;
    }

    @Override
    public String getBookAuthor() {
        Elements author = tempBook.getElementsByClass("autor");
        return author.get(FIRST_ELEMENT).getElementsByTag("a").get(FIRST_ELEMENT).text();
    }

    @Override
    public String getImageUrl() {
        Elements image = tempBook.getElementsByClass("span2 imgContainer");
        return image.get(FIRST_ELEMENT)
                .getElementsByTag("a")
                .get(FIRST_ELEMENT).getElementsByTag("img")
                .attr("data-src");
    }

    @Override
    public String getBookCategory() {
        if (bookCategory == null || bookCategory.isEmpty()) {
            Elements genre = shopConnection.getElementsByClass("searchNavHeader");
            bookCategory = genre.get(FIRST_ELEMENT).text()
                    .substring(genre.get(FIRST_ELEMENT).text().indexOf(':') + 1).trim();
            return genre.get(FIRST_ELEMENT).text()
                    .substring(genre.get(FIRST_ELEMENT).text().indexOf(':') + 1).trim();
        }
        return bookCategory;
    }

    @Override
    public String getBookTitle() {
        Elements title = tempBook.getElementsByTag("h2");
        return title.text().replaceAll("Outlet", "");
    }

    @Override
    public String getBookLink() {
        Elements bookLink = tempBook.getElementsByClass("span2 imgContainer");
        return bookLink.get(FIRST_ELEMENT)
                .getElementsByTag("a")
                .attr("href");
    }

    @Override
    public Double getBookPrice() {
        Elements price = tempBook.getElementsByClass("newPrice");
        return Double.parseDouble(price.text().replaceAll("[a-ż]+", ""));
    }

    @Override
    public String getDiscount() {
        Elements discount = tempBook.getElementsByClass("save");
        return discount.text().replaceAll("[a-ż]+", "-");
    }

    @Override
    public Bookstore getBookStore() {
        return bookstore;
    }
}


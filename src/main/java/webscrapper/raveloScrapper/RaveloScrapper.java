package webscrapper.raveloScrapper;

import com.java.academy.model.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webscrapper.BookScrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RaveloScrapper implements BookScrapper {


    private final String shopLink;
    private Document shopConnection;
    private Element tempBook;

    public RaveloScrapper(String link) {
        shopLink = link;
    }

     List<Book> prepereBookPackage() {

        List <Book> booksByGenre = new ArrayList<>();
        try {
            shopConnection = Jsoup.connect(shopLink).get();
            Elements bookContainer = shopConnection.getElementsByClass("row productBox ");

            bookContainer.forEach(book -> {
                tempBook = book;
                String image = checkImageUrl();
                String bookLink = checkBookLink();
                String title = checkBookTitle();
                String author = checkBookAuthor();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return booksByGenre;
    }

    @Override
    public String checkBookAuthor() {
        Elements author = tempBook.getElementsByClass("autor");
        author.get(0).getElementsByTag("a").text();

        return null;
    }

    @Override
    public String checkImageUrl() {
        Elements image = tempBook.getElementsByClass("span2 imgContainer");
        return image.get(0)
                .getElementsByTag("a")
                .get(0).getElementsByTag("img")
                .attr("data-src");
    }

    @Override
    public String checkBookGenre() {
        return null;
    }

    @Override
    public String checkBookTitle() {
        Elements title = tempBook.getElementsByTag("h2");
        return title.text().replaceAll("Outlet", "");
    }

    @Override
    public String checkBookLink() {
        Elements bookLink = tempBook.getElementsByClass("span2 imgContainer");
        return bookLink.get(0)
                .getElementsByTag("a")
                .attr("href");
    }

    @Override
    public Double checkBookPrice() {
        return null;
    }

    @Override
    public String checkDiscount() {
        return null;
    }
}

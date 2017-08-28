package webscrapper.raveloScrapper;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RaveloBookProvider {
    public static void main(String[] args) {
        RaveloBookProvider raveloBookProvider = new RaveloBookProvider();
        raveloBookProvider.getBooksFromRavelo();
    }

    private final String HOST = "https://www.ravelo.pl";

    private RaveloScrapper raveloScrapper;
    private Bookstore bookstore;
    private String url ="https://www.ravelo.pl/outlet";

    public List<Book> getBooksFromRavelo(){

        List<Book> raveloBooks = new ArrayList<>();
        try {
            Document raveloPromoBase = Jsoup.connect(url).get();
            Elements categories = raveloPromoBase.getElementsByClass("row showcase showcase6x1 m-books4 ");
            List<String> categoryLinks = new ArrayList<>();

            categories.forEach(element -> {
                Elements links = element.getElementsByClass("see-more");

                links.forEach(l -> {
                    categoryLinks.add(HOST + l.getElementsByTag("a").attr("href"));
                });
            });
            //last link is for toys so no need to check
            categoryLinks.remove(categoryLinks.size()-1);

            initializeBookStore();

            categoryLinks.forEach(link -> {
                raveloScrapper = new RaveloScrapper(link, bookstore);
                raveloScrapper.prepareBookPackage();

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return raveloBooks;
    }

    private void initializeBookStore() {
        this.bookstore = new Bookstore();
        this.bookstore.setName("Ravelo");
        this.bookstore.setUrl(HOST);
    }
}

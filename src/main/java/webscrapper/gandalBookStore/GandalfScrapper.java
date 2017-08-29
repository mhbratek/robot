package webscrapper.gandalBookStore;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webscrapper.BookScrapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GandalfScrapper implements BookScrapper {

    private static final int FIRST_ELEMENT = 0;
    private final static String HOST = "http://www.gandalf.com.pl";

    private Element tempProduct;
    private String url ="http://www.gandalf.com.pl/promocje/";
    private Bookstore bookstore;

    public List<Book> collectBooksFromGandalfBookstore() {
        List<Book> books = new ArrayList<>();
        initBookStore();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements pages = doc.getElementsByClass("paging_number_link");

            int pagesNumber = Integer.parseInt(pages.get(pages.size()-1).text());

            for (int page = 1; page <= pagesNumber-1; page++) {
                books.addAll(collectBooksFromSinglePage(doc));

                url = "http://www.gandalf.com.pl/promocje/" + (page + 1) + "/";
                doc = Jsoup.connect(url).get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

     void initBookStore() {
        bookstore = new Bookstore();
        bookstore.setName("Gandalf");
        bookstore.setUrl(HOST);
    }

    List<Book> collectBooksFromSinglePage(Document doc) {
        Elements prod = doc.getElementsByClass("prod");
        List<Book> singlePageBooks = new ArrayList<>();

        prod.forEach(product -> {
            tempProduct = product;
            Book book = new Book(getBookTitle(),
                    getBookAuthor(),
                    getBookCategory(),
                    getDiscount(),
                    new BigDecimal(getBookPrice()),
                    bookstore);

            book.setUrl(getBookLink());
            book.setImgUrl(getImageUrl());
            singlePageBooks.add(book);
        });
        return singlePageBooks;
    }

    Bookstore getBookStore() {
        return this.bookstore;
    }

    @Override
    public String getBookAuthor() {
        Elements author = tempProduct.getElementsByClass("h3");
        return author.text();
    }

    @Override
    public String getImageUrl() {
        Elements image = tempProduct.getElementsByTag("img");
        return (HOST + image.attr("src"));
    }

    @Override
    public String getBookCategory() {
        Document productDetails = null;
        try {
            productDetails = Jsoup.connect(getBookLink()).get();
        } catch (IOException e) {
            return "Non_specify";
        }
        Elements genre = productDetails.getElementsByClass("product_categories");
        return genre.text().substring(genre.text().lastIndexOf(":") + 1).trim();
    }

    @Override
    public String getBookTitle() {
        Elements title = tempProduct.getElementsByClass("h2");
        return title.text();
    }

    @Override
    public String getBookLink() {
        Elements links = tempProduct.getElementsByClass("h2").get(FIRST_ELEMENT).getElementsByTag("a");
        return (HOST + links.attr("href"));
    }

    @Override
    public Double getBookPrice() {
        Elements price = tempProduct.getElementsByClass("new_price");
        return Double.parseDouble(price.text()
                .replaceAll("[a-ż]+", "").replace(',', '.'));
    }

    @Override
    public String getDiscount() {
        Elements discount = tempProduct.getElementsByClass("price_dis");
        return discount.text().replaceAll("[a-z]+", "");
    }
}

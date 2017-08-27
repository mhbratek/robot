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

    private final String HOST = "http://www.gandalf.com.pl";
    private Element tempProduct;
    private String url ="http://www.gandalf.com.pl/promocje/";

    public List<Book> getBooksFromGandalf() {
        List<Book> books = new ArrayList<>();

        Bookstore bookstore = new Bookstore();
        bookstore.setName("Gandalf");
        bookstore.setUrl(HOST);

        try {

            Document doc = Jsoup.connect(url).get();
            Elements pages = doc.getElementsByClass("paging_number_link");

            int pagesNumber = Integer.parseInt(pages.get(pages.size()-1).text());

            for (int page = 1; page <= pagesNumber-1; page++) {

                Elements prod = doc.getElementsByClass("prod");

                prod.forEach(product -> {
                    tempProduct = product;
                    Book book = new Book(checkBookTitle(),
                            checkBookAuthor(),
                            checkBookGenre(),
                            checkDiscount(),
                            new BigDecimal(checkBookPrice()),
                            bookstore);

                    book.setUrl(checkBookLink());
                    book.setImgUrl(checkImageUrl());
                    books.add(book);
                });

                url = "http://www.gandalf.com.pl/promocje/" + page + "/";
                doc = Jsoup.connect(url).get();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        books.forEach(System.out::println);
        return books;
    }
    
    @Override
    public String checkBookAuthor() {
        Elements author = tempProduct.getElementsByClass("h3");
        return author.text();
    }

    @Override
    public String checkImageUrl() {
        Elements image = tempProduct.getElementsByTag("img");
        return (HOST + image.attr("src"));
    }

    @Override
    public String checkBookGenre() {
        Document productDetails = null;
        try {
            productDetails = Jsoup.connect(checkBookLink()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements genre = productDetails.getElementsByClass("product_categories");
        return genre.text().substring(genre.text().lastIndexOf(":") + 1).trim();
    }

    @Override
    public String checkBookTitle() {
        Elements title = tempProduct.getElementsByClass("h2");
        return title.text();
    }

    @Override
    public String checkBookLink() {
        Elements links = tempProduct.getElementsByClass("h2").get(0).getElementsByTag("a");
        return (HOST + links.attr("href"));
    }

    @Override
    public Double checkBookPrice() {
        Elements price = tempProduct.getElementsByClass("new_price");
        return Double.parseDouble(price.text()
                .replaceAll("[a-ż]+", "").replace(',', '.'));
    }

    @Override
    public String checkDiscount() {
        Elements discount = tempProduct.getElementsByClass("price_dis");
        return discount.text().replaceAll("[a-z]+", "");
    }

}

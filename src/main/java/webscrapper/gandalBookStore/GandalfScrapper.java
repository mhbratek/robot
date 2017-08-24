package webscrapper.gandalBookStore;


import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GandalfScrapper {

    public List<Book> getBooksFromGandalf() {

        String url = "http://www.gandalf.com.pl/k/okazje-do-60/bzab/";
        final String gandalfHost = "http://www.gandalf.com.pl";
        List<Book> books = new ArrayList<>();

        Bookstore bookstore = new Bookstore();
        bookstore.setName("Gandalf");
        bookstore.setUrl(gandalfHost);

        try {

            Document doc = Jsoup.connect(url).get();
            Elements pages = doc.getElementsByClass("paging_number_link");

            for (int page = 1; page <= pages.size(); page++) {

                Elements prod = doc.getElementsByClass("prod");

                prod.forEach(product -> {
                    Elements image = product.getElementsByTag("img");
                    String imageSrc = gandalfHost + image.attr("src");

                    Elements title = product.getElementsByClass("h2");
                    String bookTitle = title.text();

                    Elements links = title.get(0).getElementsByTag("a");
                    String shopLink = gandalfHost + links.attr("href");

                    Elements author = product.getElementsByClass("h3");
                    String bookAuthor = author.text();

                    Elements price = product.getElementsByClass("new_price");
                    String newPrice = price.text();
                    Double bookPrice = Double.parseDouble(newPrice.replaceAll("[a-ż]+", "")
                            .replace(',', '.'));

                    Elements discount = product.getElementsByClass("price_dis");
                    String bookDiscount = discount.text().replaceAll("[a-z]+", "");

                    String bookGenre = null;

                    try {
                        Document productDetails = Jsoup.connect(shopLink).get();
                        Elements genre = productDetails.getElementsByClass("product_categories");
                        bookGenre = genre.text().substring(genre.text().lastIndexOf(":") + 1).trim();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Book book = new Book(bookTitle, bookAuthor, bookGenre, bookDiscount, new BigDecimal(bookPrice), bookstore);
                    book.setUrl(shopLink);
                    book.setImgUrl(imageSrc);

                    books.add(book);

                });

                url = "http://www.gandalf.com.pl/k/okazje-do-60/bzab" + page + "/";
                doc = Jsoup.connect(url).get();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        books.forEach(System.out::println);
        return books;
    }
}

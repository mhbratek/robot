package webscrapper.gandalBookStore;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GandalfScrapper {

    public static void main(String[] args) {

        String url = "http://www.gandalf.com.pl/k/okazje-do-60/bzab/";
        final String gandalfHost = "http://www.gandalf.com.pl";
        Set<GandalfBooks> booksSet = new HashSet<>();

        try {

            Document doc = Jsoup.connect(url).get();
            Elements pages = doc.getElementsByClass("paging_number_link");

            for (int page = 1; page <= pages.size(); page ++) {

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
                        bookGenre = genre.text().substring(genre.text().lastIndexOf(":")+1).trim();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    booksSet.add(new GandalfBooks(bookTitle, bookAuthor, bookPrice, bookDiscount,
                            imageSrc, shopLink, bookGenre));

                });

                url = "http://www.gandalf.com.pl/k/okazje-do-60/bzab" + page + "/";
                doc = Jsoup.connect(url).get();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        booksSet.forEach(System.out::println);

    }
}

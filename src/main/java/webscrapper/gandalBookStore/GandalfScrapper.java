package webscrapper.gandalBookStore;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GandalfScrapper {

    public static void main(String[] args) {
        Document doc = null;
        final String gandalfHost = "www.gandalf.com.pl";
        Set<GandalfBooks> booksSet = new HashSet<>();

        try {
            doc = Jsoup.connect("http://www.gandalf.com.pl/k/okazje-do-60/bzab/").get();

            Elements prod = doc.getElementsByClass("prod");

            prod.forEach(product -> {
                Elements image = product.getElementsByTag("img");
                String imageSrc = gandalfHost+image.attr("src");

                Elements title = product.getElementsByClass("h2");
                String bookTitle = title.text();

                Elements links = title.get(0).getElementsByTag("a");
                String shopLink = gandalfHost+links.attr("href");

                Elements author = product.getElementsByClass("h3");
                String bookAuthor = author.text();

                Elements price = product.getElementsByClass("new_price");
                String newPrice = price.text();
                Double bookPrice = Double.parseDouble(newPrice.replaceAll("[a-ż]+","")
                        .replace(',','.'));

                Elements discount = product.getElementsByClass("price_dis");
                String bookDiscount = discount.text().replaceAll("[a-z]+","");

                booksSet.add(new GandalfBooks(bookTitle, bookAuthor, bookPrice, bookDiscount, imageSrc, shopLink));

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        booksSet.forEach(System.out::println);

    }
}

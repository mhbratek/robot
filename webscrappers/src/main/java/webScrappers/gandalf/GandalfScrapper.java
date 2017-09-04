package webScrappers.gandalf;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webScrappers.AbstrackBookScrapper;
import webScrappers.DocumentLoader;

import java.math.BigDecimal;

public class GandalfScrapper extends AbstrackBookScrapper {

    private String hostUrl;
    private DocumentLoader loader;

    public GandalfScrapper(DocumentLoader loader) {
        this.loader = loader;
        this.hostUrl = "http://www.gandalf.com.pl";
        initializeGandalfScrapper();
    }

    private void initializeGandalfScrapper() {
        bookstore = initBookStore("Gandalf", hostUrl);
        authorClassName = "h3";
        discountClassName = "price_dis";
        titleClassName = "h2";
    }

    @Override
    public Elements getPageToCheck(int page) {
        String url;
        if (page == 0) {
           url = "http://www.gandalf.com.pl/promocje/";
        } else {
         url = "http://www.gandalf.com.pl/promocje/" + (page + 1) + "/";
        }

        Document gandalfBooks = provideShopConnection(url, loader);

        return gandalfBooks.getElementsByClass("prod");
    }

    @Override
    public String getImageUrl(Element product) {
        return (hostUrl + product.getElementsByTag("img").attr("src"));
    }

    @Override
    public String getBookCategory(Element product) {
        Document productDetails = provideShopConnection(getBookLink(product), loader);
        Elements genre = productDetails.getElementsByClass("product_categories");
        return genre == null? "nieznany" : genre.text().substring(genre.text().lastIndexOf(':') + 1).trim();
    }

    @Override
    public String getBookLink(Element product) {
        return (hostUrl + product.getElementsByClass("h2").get(FIRST_ELEMENT)
                .getElementsByTag("a").attr("href"));
    }

    @Override
    public BigDecimal getBookPrice(Element product) {
        return new BigDecimal(Double.parseDouble(product.getElementsByClass("new_price")
                .text().replaceAll("[a-ż]+", "").replace(',', '.')));
    }

    @Override
    public String getDiscount(Element product) {
        return product.getElementsByClass(discountClassName).text()
                .replaceAll("[a-z]+", "");
    }
}




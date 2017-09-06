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
        initializeDataToScrap("Gandalf", hostUrl, "h3",
                "price_dis", "h2");
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
        String imgUrl = hostUrl + product.getElementsByTag("img").attr("src");
        return imgUrl.length() <= hostUrl.length() ? defaultImg : imgUrl;
    }

    @Override
    public String getBookCategory(Element product) {
        details = provideShopConnection(getBookLink(product), loader);
        Elements genre = details.getElementsByClass("product_categories");
        return genre == null? "nieznany" : genre.text().substring(genre.text().lastIndexOf(':') + 1).trim();
    }

    @Override
    public String getBookLink(Element product) {
        return (hostUrl + product.getElementsByClass("h2").select("a").attr("href"));
    }

    @Override
    public BigDecimal getBookPrice(Element product) {
        return BigDecimal.valueOf(Double.parseDouble(product.getElementsByClass("new_price")
                .text().replaceAll("[a-ż]+", "").replace(',', '.')));
    }

    @Override
    public String getDiscount(Element product) {
        return product.getElementsByClass(discountClassName).text()
                .replaceAll("[a-z]+", "");
    }

}




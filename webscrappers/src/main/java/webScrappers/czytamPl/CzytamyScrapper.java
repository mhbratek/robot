package webScrappers.czytamPl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webScrappers.AbstrackBookScrapper;
import webScrappers.DocumentLoader;

import java.math.BigDecimal;

public class CzytamyScrapper extends AbstrackBookScrapper {

    private String hostUrl;
    private DocumentLoader loader;

    public CzytamyScrapper(DocumentLoader loader) {
        this.loader = loader;
        this.hostUrl = "http://czytam.pl";
        initializeDataToScrap("Czytam", hostUrl, "product-author",
                "icon_rabat", "product-title");
    }

    @Override
    public Elements getPageToCheck(int page) {
        String url = "http://czytam.pl/ksiazki-promocje," + (page + 1) + ".html";
        Document czytamBooks = provideShopConnection(url, loader);

        return czytamBooks.getElementsByClass("product");
    }

    @Override
    public String getImageUrl(Element product) {
        String imgUrl = product.getElementsByClass("has-tip [tip-left] th [radius]").attr("src");
        return imgUrl.length() < hostUrl.length() ? defaultImg : imgUrl;
    }

    @Override
    public String getBookCategory(Element product) {
        details = provideShopConnection(getBookLink(product), loader);
        return details.getElementsByClass("trail") == null ? "nieznany"
                : details.getElementsByClass("trail").text();
    }

    @Override
    public String getBookLink(Element product) {
        return hostUrl + product.getElementsByClass("product-title")
                .select("a")
                .attr("href");
    }

    @Override
    public BigDecimal getBookPrice(Element product) {
        return BigDecimal.valueOf(Double.parseDouble(product.getElementsByClass("strike")
                .text()
                .replaceAll(",", ".")
                .replaceAll("[A-ż]+", "")));
    }

    @Override
    public String getSubtitle(Element product) {
        String subtitle = details.select(".show-for-medium-up").select("h1").first().text();
        return subtitle.isEmpty() ? "-" :  subtitle.replaceAll(getBookTitle(product), "");
    }
}



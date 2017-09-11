package webScrappers.matras;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webScrappers.AbstrackBookScrapper;
import webScrappers.DocumentLoader;

import java.math.BigDecimal;

public class MatrasScrapper extends AbstrackBookScrapper{

    private String hostUrl;
    private DocumentLoader loader;

    public MatrasScrapper(DocumentLoader loader) {
        this.loader = loader;
        this.hostUrl = "http://www.matras.pl";
        initializeDataToScrap("Matras", hostUrl, "item-author",
                "line discount-line", "item-title");
    }

    @Override
    public Elements getPageToCheck(int page) {
        String url = "http://www.matras.pl/ksiazki/tania-ksiazka,k,54?p=" + page;
        Document matrasBooks = provideShopConnection(url, loader);

        return matrasBooks.getElementsByClass("s-item eqh");
    }

    @Override
    public String getDiscount(Element product) {
        return product.getElementsByClass(discountClassName).text()
                .replaceAll("[A-ż]+", "-");
    }

    @Override
    public String getImageUrl(Element product) {
        String imgUrl = product.getElementsByClass("image")
                .select("img").attr("data-original");
        return imgUrl.length() < hostUrl.length() ? defaultImg : imgUrl;
    }

    @Override
    public String getBookCategory(Element product) {
        details = provideShopConnection(getBookLink(product), loader);
        return details.getElementsByClass("m-list") == null ? "nieznany" :
                details.getElementsByClass("m-list").text();
            }

    @Override
    public String getBookLink(Element product) {
        return product.getElementsByClass("right-side")
                .select("a")
                .attr("href");
    }

    @Override
    public BigDecimal getBookPrice(Element product) {
        return BigDecimal.valueOf(Double.parseDouble(product.getElementsByClass("item-price")
                .text()
                .replaceAll(",", ".")
                .replaceAll("[a-ż]+", "")));
    }

}

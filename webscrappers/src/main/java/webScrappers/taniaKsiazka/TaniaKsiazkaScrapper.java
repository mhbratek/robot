package webScrappers.taniaKsiazka;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webScrappers.AbstrackBookScrapper;
import webScrappers.DocumentLoader;

import java.math.BigDecimal;

public class TaniaKsiazkaScrapper extends AbstrackBookScrapper {

    private String hostUrl;
    private DocumentLoader loader;

    public TaniaKsiazkaScrapper(DocumentLoader loader) {
        this.loader = loader;
        this.hostUrl = "http://www.taniaksiazka.pl";
        initializeTaniaKsiazka();
    }

    private void initializeTaniaKsiazka() {
        bookstore = initBookStore("TaniaKsiazka", hostUrl);
        authorClassName = "author";
        discountClassName = "discount";
        titleClassName = "ecommerce-datalayer ";
    }

    @Override
    public Elements getPageToCheck(int page) {
        String url = "http://www.taniaksiazka.pl/promocje/id-142/page-" + (page + 1);
        Document czytamBooks = provideShopConnection(url, loader);

        return czytamBooks.getElementsByClass("list-content");
    }

    @Override
    public String getBookTitle(Element product) {
        return product.getElementsByClass(titleClassName).text().replace("do koszyka", "");
    }

    @Override
    public String getImageUrl(Element product) {
        return product.getElementsByClass("photo")
                .select("img").attr("src");
    }

    @Override
    public String getBookCategory(Element product) {
        Document details = provideShopConnection(getBookLink(product), loader);
        return details.getElementsByClass("active").size() < 1 ? "nieznany"
                : details.getElementsByClass("active").get(FIRST_ELEMENT)
                .getElementsByTag("a").attr("title");
    }

    @Override
    public String getBookLink(Element product) {
        return hostUrl + product.getElementsByClass("ecommerce-datalayer ")
                .select("a")
                .attr("href");
    }

    @Override
    public BigDecimal getBookPrice(Element product) {
        return BigDecimal.valueOf(Double.parseDouble(product.getElementsByClass("ecommerce-datalayer ")
                .attr("data-price")
                .replaceAll(",", ".")
                .replaceAll("[A-ż]+", "")));
    }

}


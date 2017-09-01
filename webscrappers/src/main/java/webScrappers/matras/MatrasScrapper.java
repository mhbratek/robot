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
        initializeMatrasScrapper();
    }

    private void initializeMatrasScrapper() {
        bookstore = initBookStore("Matras", hostUrl);
        authorClassName = "item-author";
        discountClassName = "line discount-line";
        titleClassName = "item-title";
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
        return product.getElementsByClass("image")
                .get(FIRST_ELEMENT).getElementsByTag("img").attr("data-original");
    }

    @Override
    public String getBookCategory(Element product) {
        Document details = provideShopConnection(getBookLink(product), loader);
        Elements category = details.getElementsByClass("m-list");
        return category.text().isEmpty() ? "nieznany" : category.text()
                                                    .substring(category.text()
                                                    .lastIndexOf("Książki") + "Książki".length()).trim();
            }

    @Override
    public String getBookLink(Element product) {
        Elements bookLink = product.getElementsByClass("right-side");
        return bookLink.get(FIRST_ELEMENT)
                .getElementsByTag("a")
                .attr("href");
    }

    @Override
    public BigDecimal getBookPrice(Element product) {
        return new BigDecimal(Double.parseDouble(product.getElementsByClass("item-price")
                .text()
                .replaceAll(",", ".")
                .replaceAll("[a-ż]+", "")));
    }
}

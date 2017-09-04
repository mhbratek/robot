package webScrappers.ksiegarniaPWN;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webScrappers.AbstrackBookScrapper;
import webScrappers.DocumentLoader;

import java.math.BigDecimal;

public class PWNscrapper extends AbstrackBookScrapper {

    private String hostUrl;
    private DocumentLoader loader;

    public PWNscrapper(DocumentLoader loader) {
        this.loader = loader;
        this.hostUrl = "https://ksiegarnia.pwn.pl";
        initializeCztamScrapper();
    }

    private void initializeCztamScrapper() {
        bookstore = initBookStore("PWN", hostUrl);
        authorClassName = "emp-info-author";
        discountClassName = "emp-image-label";
        titleClassName = "emp-info-title emp-two-lines";
    }

    @Override
    public Elements getPageToCheck(int page) {
        String url = "https://ksiegarnia.pwn.pl/promocje?fc_category_id=2195456&limit=96&page=" + (page + 1);
        Document pwnBooks = provideShopConnection(url, loader);

        return pwnBooks.getElementsByClass("emp-product-hover");
    }

    @Override
    public String getBookAuthor(Element product) {
        StringBuffer buffer = new StringBuffer();
        Elements element = product.getElementsByClass(authorClassName);
        int NumberOfAuthors = element.size() / 2;
        if (element.size() > 0) {
            for (int i = FIRST_ELEMENT; i < NumberOfAuthors; i++) {
                buffer.append(element.get(i).text());
            }
        } else
            buffer.append("nieznany");

        return buffer.toString();
    }

    @Override
    public String getImageUrl(Element product) {
        return product.getElementsByClass("emp-image")
                .get(FIRST_ELEMENT).getElementsByTag("img").attr("src");
    }

    @Override
    public String getBookCategory(Element product) {
        Document details = provideShopConnection(getBookLink(product), loader);
        return details.getElementsByClass("category wartosc") == null ? "nieznany"
                : details.getElementsByClass("category wartosc").last().select("a").last().text();
    }

    @Override
    public String getBookLink(Element product) {
        return hostUrl + product.getElementsByClass("emp-image")
                .get(FIRST_ELEMENT)
                .getElementsByTag("a")
                .attr("href");
    }

    @Override
    public BigDecimal getBookPrice(Element product) {
        return new BigDecimal(Double.parseDouble(product.getElementsByClass("emp-sale-price-value")
                .text()
                .replaceAll(",", ".")
                .substring(0, product.getElementsByClass("emp-sale-price-value")
                        .text().indexOf("zł"))
                .trim()));
    }

    @Override
    public String getDiscount(Element product) {
        Elements elements = product.getElementsByClass(discountClassName);
        return elements.size() > 0 ? elements.get(FIRST_ELEMENT).text() : "-";
    }

}




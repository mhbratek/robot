package webScrappers.ksiegarniaPWN;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webScrappers.AbstrackBookScrapper;
import webScrappers.DocumentLoader;
import webScrappers.JSOUPLoader;
import webScrappers.mapper.BookMapper;
import webScrappers.mapper.BookMapperByStore;

import java.math.BigDecimal;

public class PWNscrapper extends AbstrackBookScrapper {
    public static void main(String[] args) {
        PWNscrapper pwNscrapper = new PWNscrapper(new JSOUPLoader());
        BookMapper mapper = new BookMapperByStore(pwNscrapper);
        mapper.collectBooksFromBookStore();
    }

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
        String url = "https://ksiegarnia.pwn.pl/promocje?limit=96&page" + (page + 1);
        Document pwnBooks = provideShopConnection(url, loader);

        return pwnBooks.getElementsByClass("emp-product-hover");
    }

    @Override
    public String getImageUrl(Element product) {
        return product.getElementsByClass("emp-image")
                .get(FIRST_ELEMENT).getElementsByTag("img").attr("src");
    }

    @Override
    public String getBookCategory(Element product) {
        Document details = provideShopConnection(getBookLink(product), loader);
        String category = details.getElementsByClass("category wartosc").text();
        return category.isEmpty() ? "nieznany" : category;
    }

    @Override
    public String getBookLink(Element product) {
        return hostUrl + product.getElementsByClass("emp-info-container")
                .get(FIRST_ELEMENT)
                .getElementsByTag("a")
                .attr("href");
    }

    @Override
    public BigDecimal getBookPrice(Element product) {
        return new BigDecimal(Double.parseDouble(product.getElementsByClass("emp-sale-price-value")
                .text()
                .replaceAll(",", ".")
                .replaceAll("[A-ż]+", "")));
    }

}




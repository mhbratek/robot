package webScrappers.ravelo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webScrappers.AbstrackBookScrapper;
import webScrappers.DocumentLoader;
import webScrappers.JSOUPLoader;
import webScrappers.mapper.BookMapper;
import webScrappers.mapper.BookMapperByStore;

import java.math.BigDecimal;

public class RaveloScrapper extends AbstrackBookScrapper{

    private String hostUrl;
    private DocumentLoader loader;

    public RaveloScrapper(DocumentLoader loader) {
        this.loader = loader;
        this.hostUrl = "https://www.ravelo.pl";
        initializeCztamScrapper();
    }

    private void initializeCztamScrapper() {
        bookstore = initBookStore("Ravelo", hostUrl);
        authorClassName = "h3";
        discountClassName = "price_dis";
        titleClassName = "h2";
    }

    @Override
    public Elements getPageToCheck(int page) {
        String url = "https://www.ravelo.pl/szukaj.html?query=&filterActive=1&search=1&filterCategory1" +
                    "=Ksi%C4%85%C5%BCki&productsPerPage=24&filterIsSale" +
                    "=0&filterIsOutlet=1&sortPublicationDate=desc&cat_id=12881" + "&p=" + (page+1);

        Document ravelo = provideShopConnection(url, loader);

        return ravelo.getElementsByClass("row productBox ");
    }

    @Override
    public String getBookAuthor(Element product) {
        return product.getElementsByClass("autor")
                .get(FIRST_ELEMENT).getElementsByTag("a").get(FIRST_ELEMENT).text();
    }

    @Override
    public String getImageUrl(Element product) {
        return product.getElementsByClass("span2 imgContainer")
                .get(FIRST_ELEMENT)
                .getElementsByTag("a")
                .get(FIRST_ELEMENT).getElementsByTag("img")
                .attr("data-src");
    }

    @Override
    public String getBookCategory(Element product) {
        return "literatura";
    }

    @Override
    public String getBookTitle(Element product) {
        return product.getElementsByTag("h2")
                .text().replaceAll("Outlet", "");
    }

    @Override
    public String getBookLink(Element product) {
        return product.getElementsByClass("span2 imgContainer")
                .get(FIRST_ELEMENT)
                .getElementsByTag("a")
                .attr("href");
    }

    @Override
    public BigDecimal getBookPrice(Element product) {
        return new BigDecimal(Double.parseDouble(product.getElementsByClass("newPrice")
                .text()
                .replaceAll("[a-ż]+", "")));
    }

    @Override
    public String getDiscount(Element product) {
        return product.getElementsByClass("save")
                .text()
                .replaceAll("[a-ż]+", "-");
    }

}


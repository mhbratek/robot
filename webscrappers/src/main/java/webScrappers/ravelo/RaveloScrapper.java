package webScrappers.ravelo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webScrappers.AbstrackBookScrapper;
import webScrappers.DocumentLoader;

import java.math.BigDecimal;

public class RaveloScrapper extends AbstrackBookScrapper{

    private String hostUrl;
    private DocumentLoader loader;

    public RaveloScrapper(DocumentLoader loader) {
        this.loader = loader;
        this.hostUrl = "https://www.ravelo.pl";
        initializeDataToScrap("Ravelo", hostUrl, "h3",
                "price_dis", "h2");
    }

    @Override
    public Elements getPageToCheck(int page) {
        String url = "https://www.ravelo.pl/szukaj.html?query=&filterActive=1&search=1&filterCategory1" +
                    "=Ksi%C4%85%C5%BCki&productsPerPage=24&filterIsSale" +
                    "=0&filterIsOutlet=1&sortPublicationDate=desc&cat_id=12881" + "&p=" + (page+1);

        Document ravelo = provideShopConnection(url, loader);
//        pagesToCheck = Integer.valueOf(ravelo.getElementsByClass("pagination")
//                .select("div").select("input").attr("value"));
        return ravelo.getElementsByClass("row productBox ");
    }

    @Override
    public String getBookAuthor(Element product) {
        return product.getElementsByClass("autor")
                .select("a").size() > 0 ? product.getElementsByClass("autor")
                .select("a").get(FIRST_ELEMENT).text() : "nieznany";
    }

    @Override
    public String getImageUrl(Element product) {
        String imgUrl = product.getElementsByClass("span2 imgContainer")
                .select("img")
                .attr("data-src");
        return imgUrl.length() < hostUrl.length() ? defaultImg : imgUrl;
    }

    @Override
    public String getBookCategory(Element product) {
        return "literatura piękna";
    }

        @Override
    public String getBookTitle(Element product) {
        return product.getElementsByTag("h2")
                .text().replaceAll("Outlet", "");
    }

    @Override
    public String getBookLink(Element product) {
        return product.getElementsByClass("span2 imgContainer")
                .select("a")
                .attr("href");
    }

    @Override
    public BigDecimal getBookPrice(Element product) {
        return BigDecimal.valueOf(Double.parseDouble(product.getElementsByClass("newPrice")
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


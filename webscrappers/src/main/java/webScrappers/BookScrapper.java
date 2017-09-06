package webScrappers;

import com.java.academy.model.Bookstore;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;

public interface BookScrapper {

    Elements getPageToCheck(int page);
    String getBookTitle(Element product);
    String getBookAuthor(Element product);
    String getBookCategory(Element product);
    String getImageUrl(Element product);
    String getBookLink(Element product);
    String getSubtitle(Element product);
    BigDecimal getBookPrice(Element product);
    String getDiscount(Element product);

    Bookstore getBookStore();
}


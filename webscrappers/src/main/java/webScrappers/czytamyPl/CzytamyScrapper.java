package webScrappers.czytamyPl;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import webScrappers.BookScrapper;
import webScrappers.DocumentLoader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CzytamyScrapper implements BookScrapper {

    private static final String HOST = "http://czytam.pl";
    private static final int FIRST_ELEMENT = 0;

    private String url;
    private Bookstore bookstore;
    private Element currentBook;
    private DocumentLoader loader;

    public CzytamyScrapper(DocumentLoader loader) {
        this.loader = loader;
        this.bookstore = initBookStore("Czytamy", HOST);
    }

    public List<Book> collectBooksFromCzytamyPl() {
        List<Book> books = new ArrayList<>();

        for (int page = 0; page < 10; page++) {
            url = "http://czytam.pl/ksiazki-promocje," + (page + 1) + ".html";
            Document matrasBooks = provideShopConnection(url, loader);
            books.addAll(collectBooksFromSinglePage(matrasBooks));
        }
        return books;
    }

    @Override
    public List<Book> collectBooksFromSinglePage(Document doc) {
        Elements prod = doc.getElementsByClass("product");
        List<Book> singlePageBooks = new ArrayList<>();

        prod.forEach(product -> {
            currentBook = product;
            singlePageBooks.add(setupBook());
        });

        return singlePageBooks;
    }

    @Override
    public String getBookAuthor() {
        return getPropertyByClassName("product-author", currentBook);
    }

    @Override
    public String getImageUrl() {
        Elements image = currentBook.getElementsByClass("has-tip [tip-left] th [radius]");
        return image.get(FIRST_ELEMENT).getElementsByTag("img").attr("src");
    }

    @Override
    public String getBookCategory() {
        Document details = provideShopConnection(getBookLink(), loader);
        Elements category = details.getElementsByClass("headline-azure");
        return category.text().isEmpty() ? "nieznany" : category.text().replaceAll(getBookAuthor(), "").trim();
    }

    @Override
    public String getBookTitle() {
        return getPropertyByClassName("product-title", currentBook);
    }

    @Override
    public String getBookLink() {
        Elements bookLink = currentBook.getElementsByClass("product-title");
        return HOST + bookLink.get(FIRST_ELEMENT)
                .getElementsByTag("a")
                .attr("href");
    }

    @Override
    public Double getBookPrice() {
        return Double.parseDouble(getPropertyByClassName("strike", currentBook)
                .replaceAll(",", ".")
                .replaceAll("[A-ż]+", ""));
    }

    @Override
    public String getDiscount() {
        return getPropertyByClassName("icon_rabat", currentBook);
    }

    @Override
    public Bookstore getBookStore() {
        return bookstore;
    }
}



package webScrappers.marters;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import webScrappers.BookScrapper;
import webScrappers.DocumentLoader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MatrasScrapper implements BookScrapper{

    private static final String HOST = "http://www.matras.pl";
    private static final int FIRST_ELEMENT = 0;

    private String url;
    private Bookstore bookstore;
    private Element currentBook;
    private DocumentLoader loader;

    public MatrasScrapper(DocumentLoader loader) {
        this.loader = loader;
        this.bookstore = initBookStore("Matras", HOST);
    }

    public List<Book> collectBooksFromMatras() {
        List<Book> books = new ArrayList<>();

        for (int page = 1; page < 10; page++) {
            url = "http://www.matras.pl/ksiazki/tania-ksiazka,k,54?p=" + page;
            Document matrasBooks = provideShopConnection(url, loader);
            books.addAll(collectBooksFromSinglePage(matrasBooks));
        }

        return books;
    }

    @Override
    public List<Book> collectBooksFromSinglePage(Document doc) {
        Elements prod = doc.getElementsByClass("s-item eqh");
        List<Book> singlePageBooks = new ArrayList<>();

        prod.forEach(product -> {
            currentBook = product;
            singlePageBooks.add(setupBook());
        });

        return singlePageBooks;
    }

    @Override
    public Bookstore getBookStore() {
        return this.bookstore;
    }

    @Override
    public String getBookAuthor() {
        return getPropertyByClassName("item-author", currentBook);
    }

    @Override
    public String getImageUrl() {
        Elements image = currentBook.getElementsByClass("image");
        return image.get(FIRST_ELEMENT).getElementsByTag("img").attr("data-original");
    }

    @Override
    public String getBookCategory() {
        Document details = provideShopConnection(getBookLink(), loader);
        Elements category = details.getElementsByClass("m-list");
        return category.text().isEmpty() ? "nieznany" : category.text()
                                                    .substring(category.text()
                                                    .lastIndexOf("Książki") + "Książki".length()).trim();
            }

    @Override
    public String getBookTitle() {
        return getPropertyByClassName("item-title", currentBook);
    }

    @Override
    public String getBookLink() {
        Elements bookLink = currentBook.getElementsByClass("right-side");
        return bookLink.get(FIRST_ELEMENT)
                .getElementsByTag("a")
                .attr("href");
    }

    @Override
    public Double getBookPrice() {
        return Double.parseDouble(getPropertyByClassName("item-price", currentBook)
                .replaceAll(",", ".")
                .replaceAll("[a-ż]+", ""));
    }

    @Override
    public String getDiscount() {
        return getPropertyByClassName("line discount-line", currentBook)
                .replaceAll("[A-ż]+", "-");
    }
}

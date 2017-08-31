package webScrappers.gandalf;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import webScrappers.BookScrapper;
import webScrappers.DocumentLoader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class GandalfScrapper  implements BookScrapper {

    private static final int FIRST_ELEMENT = 0;
    private static final String HOST = "http://www.gandalf.com.pl";

    private Element currentBook;
    private String url ="http://www.gandalf.com.pl/promocje/";
    private Bookstore bookstore;
    private DocumentLoader loader;

    public GandalfScrapper(DocumentLoader loader) {
        this.loader = loader;
        this.bookstore = initBookStore("Gandalf", HOST);
    }

    public List<Book> collectBooksFromGandalfBookstore() {
        List<Book> books = new ArrayList<>();

            Document doc = provideShopConnection(url, loader);
//            Elements pages = doc.getElementsByClass("paging_number_link");
//            int pagesNumber = Integer.parseInt(pages.get(pages.size()-1).text());
//            for (int page = 1; page < pagesNumber-1; page++) { //to long to better performance just 5 pages
        for (int page = FIRST_ELEMENT; page < 2; page++) {
                books.addAll(collectBooksFromSinglePage(doc));

                url = "http://www.gandalf.com.pl/promocje/" + (page + 1) + "/";
                doc = provideShopConnection(url, loader);
            }

        return books;
    }

    @Override
    public Bookstore getBookStore() {
        return this.bookstore;
    }

    @Override
    public List<Book> collectBooksFromSinglePage(Document doc) {
        Elements prod = doc.getElementsByClass("prod");
        List<Book> singlePageBooks = new ArrayList<>();

        prod.forEach(product -> {
            currentBook = product;
            singlePageBooks.add(setupBook());
        });
        return singlePageBooks;
    }

    @Override
    public String getBookAuthor() {
        return getPropertyByClassName("h3", currentBook);
    }

    @Override
    public String getImageUrl() {
        Elements image = currentBook.getElementsByTag("img");
        return (HOST + image.attr("src"));
    }

    @Override
    public String getBookCategory() {
        Document productDetails = provideShopConnection(getBookLink(), loader);
        Elements genre = productDetails.getElementsByClass("product_categories");
        return genre.text().substring(genre.text().lastIndexOf(':') + 1).trim();
    }

    @Override
    public String getBookTitle() {
        return getPropertyByClassName("h2", currentBook);
    }

    @Override
    public String getBookLink() {
        Elements links = currentBook.getElementsByClass("h2").get(FIRST_ELEMENT).getElementsByTag("a");
        return (HOST + links.attr("href"));
    }

    @Override
    public Double getBookPrice() {
        return Double.parseDouble(getPropertyByClassName("new_price", currentBook)
                .replaceAll("[a-ż]+", "").replace(',', '.'));
    }

    @Override
    public String getDiscount() {
        return getPropertyByClassName("price_dis", currentBook)
                .replaceAll("[a-z]+", "");
    }
}




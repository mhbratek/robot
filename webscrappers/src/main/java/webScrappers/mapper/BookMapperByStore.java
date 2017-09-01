package webScrappers.mapper;

import com.java.academy.model.Book;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webScrappers.BookScrapper;

import java.util.ArrayList;
import java.util.List;

public class BookMapperByStore implements BookMapper {

    public static final int FIRST = 0;
    private BookScrapper bookScrapper;
    private int totalPageToCheck;

    public BookMapperByStore(BookScrapper bookScrapper) {
        this.bookScrapper = bookScrapper;
    }

    @Override
    public List<Book> collectBooksFromBookStore() {

        List<Book> books = new ArrayList<>();

        totalPageToCheck = 5;
        for (int page = FIRST; page < totalPageToCheck; page++) {
            Elements booksFromStore = bookScrapper.getPageToCheck(page);

            booksFromStore.forEach(product -> books.add(setupBook(product)));
        }
        return books;
    }

    private Book setupBook(Element product) {
        Book singleBook = new Book(bookScrapper.getBookTitle(product),
                bookScrapper.getBookAuthor(product),
                bookScrapper.getBookCategory(product),
                bookScrapper.getDiscount(product),
                bookScrapper.getBookPrice(product),
                bookScrapper.getBookStore());

        singleBook.setUrl(bookScrapper.getBookLink(product));
        singleBook.setImgUrl(bookScrapper.getImageUrl(product));

        return singleBook;
        }

}

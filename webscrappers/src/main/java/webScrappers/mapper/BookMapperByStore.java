package webScrappers.mapper;

import com.java.academy.model.Book;
import logger.RLog;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webScrappers.BookScrapper;

import java.util.ArrayList;
import java.util.List;

public class BookMapperByStore implements BookMapper {

    private static final int FIRST = 0;
    private BookScrapper bookScrapper;
    private int totalPageToCheck;

    public BookMapperByStore() {
        setTotalPageToCheck(2);
    }

    @Override
    public List<Book> collectBooksFromBookStore(BookScrapper bookScrapper) {

        this.bookScrapper = bookScrapper;
        List<Book> books = new ArrayList<>();

        for (int page = FIRST; page < totalPageToCheck; page++) {

            Elements booksFromStore = bookScrapper.getPageToCheck(page);
            for (Element product : booksFromStore) {
                try {
                    books.add(setupBook(product));
                } catch (RuntimeException ex) {
                    RLog.error(RLog.getLogger(getClass()), ex.getMessage());
                    continue;
                }
            }
        }
        RLog.info(RLog.getLogger(getClass()), ("Collected: " + books.size()+ " books from "
                + bookScrapper.getBookStore().getName()));
        return books;
    }

    private Book setupBook(Element product) {

        Book singleBook = new Book(bookScrapper.getBookTitle(product),
                bookScrapper.getBookAuthor(product),
                bookScrapper.getBookCategory(product),
                bookScrapper.getDiscount(product),
                bookScrapper.getBookPrice(product),
                bookScrapper.getBookStore());

        singleBook.setSubtitle(bookScrapper.getSubtitle(product));
        singleBook.setUrl(bookScrapper.getBookLink(product));
        singleBook.setImgUrl(bookScrapper.getImageUrl(product));

        System.out.println(singleBook.getSubtitle());
        return singleBook;
        }

    public void setTotalPageToCheck(int totalPageToCheck) {
        this.totalPageToCheck = totalPageToCheck;
    }

    public int getTotalPageToCheck() {
        return totalPageToCheck;
    }
}

package googlebookstore;



import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author bratek
 * @since 24.08.17
 */
public class BookMapper {

    private static final String DEFAULT_CATEGORY = "Book";
    private static final int DEFAULT_PRICE = 0;
    private static final String DEFAULT_AUTHOR = "Unknown";

    public static List<Book> mapFromGoogleBookStore(GoogleBook googleBook, Bookstore bookstore) {
        List<Book> books = new ArrayList<>();

        for (Item book : googleBook.getItems()) {
            Book bookToAdd = new Book();

            bookToAdd.setVersion(1L);
            bookToAdd.setTitle(book.getTitle());
            assignAuthors(book, bookToAdd);
            bookToAdd.setBookstore(bookstore);
            assignPrice(book, bookToAdd);
            bookToAdd.setUrl(book.getLink());
            assignPromoDetails(book, bookToAdd);
            assignImageURL(book, bookToAdd);
            assignCategory(book, bookToAdd);
            assignSubtitle(book, bookToAdd);


            books.add(bookToAdd);
        }

        return books;
    }

    private static void assignSubtitle(Item book, Book bookToAdd) {
        bookToAdd.setSubtitle(book.getVolumeInfo().getSubtitle());
    }

    static void assignCategory(Item book, Book bookToAdd) {
        if (ifBookHasCategory(book)) {
            bookToAdd.setCategory(book.getCategory());
        } else {
            bookToAdd.setCategory(DEFAULT_CATEGORY);
        }
    }

    static void assignImageURL(Item book, Book bookToAdd) {
        if(ifBookHasImage(book)) {
            bookToAdd.setImgUrl(book.getImageLink());
        } else {
            bookToAdd.setImgUrl("/resource/img/default-book-cover.png");
        }
    }

    static void assignPrice(Item book, Book bookToAdd) {
        if (ifBookHasPrice(book)) {
            bookToAdd.setPrice(new BigDecimal(book.getPrice()));
        } else {
            bookToAdd.setPrice(new BigDecimal(DEFAULT_PRICE));
        }
    }
    static void assignPromoDetails(Item book, Book bookToAdd) {
        if (ifBookHasPrice(book))
            bookToAdd.setPromoDetails(countPromoDetails(book));
    }

    static void assignAuthors(Item book, Book bookToAdd) {
        if (isAuthorExists(book)){
            bookToAdd.setAuthor(book.getAuthors());
        } else {
            bookToAdd.setAuthor(DEFAULT_AUTHOR);
        }
    }

    private static boolean ifBookHasCategory(Item book) {
        return book.getVolumeInfo().getMainCategory() != null;
    }

    private static boolean ifBookHasImage(Item book) {
        return book.getVolumeInfo().getImageLinks() != null;
    }

    private static boolean ifBookHasPrice(Item book) {
        return book.getSaleInfo() != null;
    }

    private static String countPromoDetails(Item book) {
        return String.valueOf((int)(((book.getSaleInfo().getListPrice().getAmount() -
                book.getSaleInfo().getRetailPrice().getAmount()) /
        book.getSaleInfo().getListPrice().getAmount()) * 100)) + "%";
    }

    private static boolean isAuthorExists(Item book) {
        return book.getVolumeInfo().getAuthors() != null;
    }
}

package webScrappers.mapper;

import com.java.academy.model.Book;
import webScrappers.BookScrapper;

import java.util.List;

public interface BookMapper {

    List<Book> collectBooksFromBookStore(BookScrapper bookScrapper);

}

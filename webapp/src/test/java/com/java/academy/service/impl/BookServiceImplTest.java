package com.java.academy.service.impl;

import com.java.academy.dao.BookDao;
import com.java.academy.dao.BookstoreDao;
import com.java.academy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * @author bratek
 * @since 05.09.17
 */
public class BookServiceImplTest {


    private BookDao bookDao;

    private BookstoreDao bookstoreDao;

    @Test
    public void shouldReturnAllBooks () {
        BookServiceImpl bookService = new BookServiceImpl();
        BookDao bookDaoMock = mock(BookDao.class);
        bookService.setBookDao(bookDaoMock);
        List<Book> books = bookService.getAllBooks();

        verify(bookDaoMock, times(1)).findAll();
    }



    @DataProvider
    public static Object[][] shouldReturnParams() {
        return new Object[][] {
                {"title", "Title"},
                {"author", "Author"},
                {"category", "Category"},
                {"bookstore", "book"},
                {"version", "1"}
        };
    }
    @Test(dataProvider = "shouldReturnParams")
    public void shouldFilterByGivenParams(String filterName, String value) {
        Book book = new Book();
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setCategory("Category");
        book.setBookstore(new Bookstore("book", "book"));
        book.setVersion(1L);
        BookServiceImpl bookService = new BookServiceImpl();

        assertEquals(bookService.filterByParams(Collections.singletonList(book), filterName, value).size(),1);

    }

    @Test
    public void shouldFilterGivenBookByRange() {
        BookServiceImpl bookService = new BookServiceImpl();

        Book book = new Book();
        book.setPrice(new BigDecimal(20L));

        assertEquals(bookService.filterByPrice(Collections.singletonList(book), Arrays.asList("10", "20")).size(), 1);
    }

    @Test
    public void shouldAddBook () {
        BookServiceImpl bookService = new BookServiceImpl();

        Book bookMock = mock(Book.class);
        when(bookMock.getBookstore()).thenReturn(new Bookstore("Dummy", "Dummy"));

        BookDao bookDaoMock = mock(BookDao.class);
        BookstoreDao bookstoreDaoMock = mock(BookstoreDao.class);

        bookService.setBookDao(bookDaoMock);
        bookService.setBookstoreDao(bookstoreDaoMock);

        when(bookstoreDaoMock.getBookstoreByName(anyString())).thenReturn(null);

        bookService.addBook(bookMock);

        verify(bookstoreDaoMock, times(1)).save(bookMock.getBookstore());
    }
}
package com.java.academy.service.impl;

import com.java.academy.dao.BookDao;
import com.java.academy.dao.BookstoreDao;
import com.java.academy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import java.math.BigDecimal;
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


    @Test
    public void shouldFilterByTitle() {
        BookServiceImpl bookService = new BookServiceImpl();
        BookDao bookDaoMock = mock(BookDao.class);
        bookService.setBookDao(bookDaoMock);

        bookService.getBooksByFilter("title", "dummy");

        verify(bookDaoMock, times(1)).getBooksByTitleContaining("dummy");
    }

    @Test
    public void shouldFilterByAuthor() {
        BookServiceImpl bookService = new BookServiceImpl();
        BookDao bookDaoMock = mock(BookDao.class);
        bookService.setBookDao(bookDaoMock);

        bookService.getBooksByFilter("author", "dummy");

        verify(bookDaoMock, times(1)).getBooksByAuthorContaining("dummy");
    }

    @Test
    public void shouldFilterByCategory() {
        BookServiceImpl bookService = new BookServiceImpl();
        BookDao bookDaoMock = mock(BookDao.class);
        bookService.setBookDao(bookDaoMock);

        bookService.getBooksByFilter("category", "dummy");

        verify(bookDaoMock, times(1)).getBooksByCategoryContaining("dummy");
    }

    @Test
    public void shouldFilterByBookStoreName() {
        BookServiceImpl bookService = new BookServiceImpl();
        BookDao bookDaoMock = mock(BookDao.class);
        bookService.setBookDao(bookDaoMock);

        bookService.getBooksByFilter("bookstore", "dummy");

        verify(bookDaoMock, times(1)).getBooksByBookstoreNameContaining("dummy");
    }

    @Test
    public void shouldUseDefaultFilter() {
        BookServiceImpl bookService = new BookServiceImpl();
        BookDao bookDaoMock = mock(BookDao.class);
        bookService.setBookDao(bookDaoMock);

        bookService.getBooksByFilter("dummy", "dummy");

        verify(bookDaoMock, times(1)).getBooksByCategoryContaining("dummy");
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
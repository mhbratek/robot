package com.java.academy.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.java.academy.model.Bookstore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.academy.dao.BookDao;
import com.java.academy.dao.BookstoreDao;
import com.java.academy.model.Book;
import com.java.academy.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	private BookDao bookDao;
	private BookstoreDao bookstoreDao;

	@Autowired
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Autowired
	public void setBookstoreDao(BookstoreDao bookstoreDao) {
		this.bookstoreDao = bookstoreDao;
	}

	public List<Book> getAllBooks() {
		return bookDao.findAll();
	}

	public Book getBookById(String bookId) {
		return bookDao.getBookById(bookId);
	}

	public List<Book> getOnlyNewBooks() {
		return bookDao.getBooksByVersion(1L);
	}

	@Override
	public List<Book> getBooksByParams(Map<String, List<String>> filterParams) {
		List<Book> allBooks = getAllBooks(); //TODO reduce amount of books by searching with filter
		for(Map.Entry<String, List<String>> filter: filterParams.entrySet()) {
			if(filter.getKey().equals("price")) {
				allBooks = filterByPrice(allBooks, filter.getValue());
			} else if(filter.getValue().get(0).equals("undefined")) {
				continue;
			} else {
				allBooks = filterByParams(allBooks, filter.getKey(), filter.getValue().get(0));
			}
		}
		return allBooks;
	}

	List<Book> filterByParams(List<Book> allBooks, String filterName, String value) {
		List<Book> filteredBooks = new ArrayList<>();
		Filter filter = Filter.AUTHOR.getFilter(filterName);
		for (Book book: allBooks) {
			if(filter.bookFits(book, value)) {
				filteredBooks.add(book);
			}
		}
		return filteredBooks;
	}

	List<Book> filterByPrice(List<Book> allBooks, List<String> value) {
		List<Book> booksToReturn = new ArrayList<>();
		for (Book book : allBooks) {
			if (isBiggerOrEqualsThan(value.get(0), book) && isLessOrEqualsThan(value.get(1), book)) {
				booksToReturn.add(book);
			}
		}
		return booksToReturn;
	}

	private boolean isBiggerOrEqualsThan(String downLimit, Book book) {
		return (new BigDecimal(downLimit)).compareTo(book.getPrice()) <= 0;
	}

	private boolean isLessOrEqualsThan(String upLimit, Book book) {
		return (new BigDecimal(upLimit)).compareTo(book.getPrice()) >= 0;
	}

	public void addBook(Book book) {
		Bookstore bookstore = bookstoreDao.getBookstoreByName(book.getBookstore().getName());
		if(isItANewBookStore(bookstore)) {
			bookstore = bookstoreDao.save(book.getBookstore());
		}
		book.setBookstore(bookstore);

		Book bookFromBase = bookDao.getBookByTitleAndAuthorAndBookstore(book.getTitle(), book.getAuthor(), book.getBookstore());
		if(bookFromBase != null) {
			bookFromBase.incrementVersion();
		}
		if(bookFromBase == null) {
			bookFromBase = book;
		}
		bookFromBase.setPrice(book.getPrice());

		bookDao.save(bookFromBase);
	}

	private boolean isItANewBookStore(Bookstore bookstore) {
		return bookstore == null;
	}

}

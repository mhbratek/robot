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
			System.out.println(filter.getKey());
			System.out.println(filter.getValue());
			System.out.println(allBooks.size());
			if(filter.getKey().equals("price")) {
				allBooks = filterByPrice(allBooks, filter.getValue());
			} else if(filter.getValue().get(0).equals("undefined")) {
				continue;
			} else {
				allBooks = filterByParams(allBooks, filter.getKey(), filter.getValue().get(0));
			}
			System.out.println(allBooks.size());
		}
		return allBooks;
	}
//TODO REFACTOR 
	private List<Book> filterByParams(List<Book> allBooks, String filter, String value) {
		List<Book> booksToReturn = new ArrayList<>();
		switch (filter) {
			case "title":
				for(Book book: allBooks) {
					if(book.getTitle().toLowerCase().contains(value.toLowerCase())) {
						booksToReturn.add(book);
					}
				}
				break;
			case "author":
				for(Book book: allBooks) {
					if(book.getAuthor().toLowerCase().contains(value.toLowerCase())) {
						booksToReturn.add(book);
					}
				}
				break;
			case "category":
				for(Book book: allBooks) {
					if(book.getCategory().toLowerCase().contains(value.toLowerCase())) {
						booksToReturn.add(book);
					}
				}
				break;
			case "bookstore":
				for(Book book: allBooks) {
					if(book.getBookstore().getName().toLowerCase().contains(value.toLowerCase())) {
						booksToReturn.add(book);
					}
				}
				break;
			case "version":
				//TODO checking version
				booksToReturn.addAll(allBooks);
				break;
		}
		return booksToReturn;
	}


	private List<Book> filterByPrice(List<Book> allBooks, List<String> value) {
		List<Book> booksToReturn = new ArrayList<>();
		for (Book book : allBooks) {
			if ((new BigDecimal(value.get(0))).compareTo(book.getPrice()) <= 0 && (new BigDecimal(value.get(1))).compareTo(book.getPrice()) >= 0) {
				booksToReturn.add(book);
			}
		}
		return booksToReturn;
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

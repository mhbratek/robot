package com.java.academy.service.impl;

import java.util.List;

import com.java.academy.model.Bookstore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.academy.dao.BookDao;
import com.java.academy.dao.BookstoreDao;
import com.java.academy.model.Book;
import com.java.academy.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDao bookDao;
	@Autowired
	private BookstoreDao bookstoreDao;

	public List<Book> getAllBooks() {
		return bookDao.findAll();
	}

	public Book getBookById(String bookId) {
		return bookDao.getBookById(bookId);
	}

	public List<Book> getBooksByFilter(String filter, String value) {
		switch (filter) {
			case "title":
				return bookDao.getBooksByTitleContaining(value);
			case "author":
				return bookDao.getBooksByAuthorContaining(value);
			case "category":
				return bookDao.getBooksByCategoryContaining(value);
			case "bookstore":
				return bookDao.getBooksByBookstoreNameContaining(value);
			default:
				return bookDao.getBooksByAuthorContaining(value);
		}
	}

	public void addBook(Book book) {
		bookstoreDao.save(book.getBookstore());
		bookDao.save(book);
	}

	public void addBooksFromLibrary(List<Book> books) {
		Bookstore bookstore = bookstoreDao.getBookstoreByName(books.get(0).getBookstore().getName());
		if(bookstore == null) {
			bookstore = bookstoreDao.save(books.get(0).getBookstore());
		}
		Bookstore finalBookstore = bookstore;
		books.forEach(book -> {
			book.setBookstore(finalBookstore);
		});
		bookDao.save(books);
	}
}

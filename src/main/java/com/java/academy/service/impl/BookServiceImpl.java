package com.java.academy.service.impl;

import java.util.List;

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

	public List<Book> getBooksByCategory(String genre) {
		return bookDao.getBooksByCategory(genre);
	}
	
	public List<Book> getBooksByAuthor(String author) {
		return bookDao.getBooksByAuthor(author);
	}

	public void addBook(Book book) {
		bookstoreDao.save(book.getBookstore());
		bookDao.save(book);
	}

	public void addBooksFromLibrary(List<Book> books) {
		bookstoreDao.save(books.get(0).getBookstore());
		bookDao.save(books);
	}

	public List<Book> getBooksFromBookstore(String bookstoreName) {
		return bookDao.getBooksByBookstore(bookstoreName);
	}
}

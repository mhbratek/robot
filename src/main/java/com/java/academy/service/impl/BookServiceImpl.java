package com.java.academy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.academy.dao.BookDao;
import com.java.academy.model.Book;
import com.java.academy.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDao bookDao;

	public List<Book> getAllBooks() {
		return bookDao.findAll();
	}

	public Book getBookById(String bookId) {
		return bookDao.getBookById(bookId);
	}

	public List<Book> getBooksByGenre(String genre) {
		return bookDao.getBooksByGenre(genre);
	}

	public void addBook(Book book) {
		bookDao.save(book);
	}
}

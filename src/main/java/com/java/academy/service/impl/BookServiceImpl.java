package com.java.academy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.academy.dao.BookRepository;
import com.java.academy.model.Book;
import com.java.academy.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;

	public List<Book> getAllBooks() {
		return bookRepository.getAllBooks();
	}

	public Book getBookById(String bookId) {
		return bookRepository.getBookById(bookId);
	}

	public List<Book> getBooksByGenre(String genre) {
		return bookRepository.getBooksByGenre(genre);
	}

	public void addBook(Book book) {
		bookRepository.addBook(book);
	}
}

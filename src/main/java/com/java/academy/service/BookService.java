package com.java.academy.service;

import java.util.List;

import com.java.academy.model.Book;

public interface BookService {
	
	List<Book> getAllBooks();
	
	Book getBookById(String bookId);
	
	List<Book> getBooksByGenre(String genre);
	
	void addBook(Book book);
}

package com.java.academy.dao;

import java.util.List;

import com.java.academy.model.Book;

public interface BookRepository {

	List<Book> getAllBooks();
	
	Book getBookById(String bookId);
	
	List<Book> getBooksByGenre(String genre);
	
	void addBook(Book book);
}

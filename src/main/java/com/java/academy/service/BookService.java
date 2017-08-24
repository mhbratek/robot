package com.java.academy.service;

import java.util.List;

import com.java.academy.model.Book;

public interface BookService {
	
	List<Book> getAllBooks();
	
	Book getBookById(String bookId);
	
	List<Book> getBooksByCategory(String category);
	
	List<Book> getBooksByAuthor(String author);
	
	List<Book> getBooksFromBookstore(String bookstoreName);
	
	void addBook(Book book);

	void addBooksFromLibrary(List<Book> books);
}

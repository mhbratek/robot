package com.java.academy.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.java.academy.model.Book;

public interface BookService {
	
	List<Book> getAllBooks();
	
	Book getBookById(String bookId);

	List<Book> getBooksByParams(Map<String, List<String>> filterParams);

	List<Book> getOnlyNewBooks();
	
	void addBook(Book book);
}

package com.java.academy.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.java.academy.model.Book;

public interface BookService {
	
	List<Book> getAllBooks();
	
	Book getBookById(String bookId);
	
	List<Book> getBooksByFilter(String filter, String value);

	Set<Book> getBooksByParams(String filter, String value, Map<String, List<String>> filterParams);
	
	void addBook(Book book);
}

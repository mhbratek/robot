package com.java.academy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.java.academy.dao.BookRepository;
import com.java.academy.exception.BookNotFoundException;
import com.java.academy.model.Book;

//TODO change to SQL repository
@Repository
public class FakeBookRepository implements BookRepository {
	
	private List<Book> listOfBooks = new ArrayList<Book>();
	
	public FakeBookRepository() {
		Book potop = new Book(1l, "Potop", "Henryk Sienkiewicz", "history novel", "-25%", 32);
		Book lalka = new Book(2l, "Lalka", "Boles³aw Prus", "novel", "-25%", 25);
		Book krewElfow = new Book(3l, "Krew Elfów", "Andrzej Sapkowski", "fantasy", "-15%", 30);
		
		listOfBooks.add(potop);
		listOfBooks.add(lalka);
		listOfBooks.add(krewElfow);
	}
	
	
	public List<Book> getAllBooks() {
		return listOfBooks;
	}
	
	public Book getBookById(String bookId) {
		Book bookById = null;
		
		for(Book book : listOfBooks) {
			if(book != null && book.getId() != null && book.getId().equals(bookId)){
				bookById = book;
				break;
			}
		}
		
		if(bookById == null){
			throw new BookNotFoundException("Brak ksi¹¿ki o wskazanym id: " + bookId);
		}
		
		return bookById;
	}
	
	public List<Book> getBooksByGenre(String genre) {
		List<Book> booksByCategory = new ArrayList<Book>();
			
		for(Book book: listOfBooks) {
			if(genre.equalsIgnoreCase(book.getGenre())){
				booksByCategory.add(book);
			}
		}
		
		if(booksByCategory.isEmpty()){
			throw new BookNotFoundException("Brak ksi¹¿ek o wskazanym dziale: " + genre);
		}
		
		return booksByCategory;
	}
	
	public void addBook(Book book) {
		listOfBooks.add(book);
	}
}

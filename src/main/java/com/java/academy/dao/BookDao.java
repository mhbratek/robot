package com.java.academy.dao;

import com.java.academy.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

	List<Book> findAll();
	
	Book getBookById(String bookId);
	
	List<Book> getBooksByCategory(String category);

	List<Book> getBooksByAuthor(String author);
	
	List<Book> getBooksByBookstore(String bookstore_name);
}

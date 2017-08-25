package com.java.academy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.academy.model.Book;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

	List<Book> findAll();
	
	Book getBookById(String bookId);
	
	List<Book> getBooksByCategoryContaining(String category);

	List<Book> getBooksByAuthorContaining(String author);
	
	List<Book> getBooksByBookstoreContaining(String bookstore_name);

	List<Book> getBooksByTitleContaining(String title);

}

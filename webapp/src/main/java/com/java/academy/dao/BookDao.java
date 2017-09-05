package com.java.academy.dao;

import com.java.academy.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

	List<Book> findAll();
	
	Book getBookById(String bookId);

	Book getBookByTitle(String title);
	
	List<Book> getBooksByCategoryContaining(String category);

	List<Book> getBooksByAuthorContaining(String author);
	
	List<Book> getBooksByBookstoreNameContaining(String bookstore_name);

	List<Book> getBooksByTitleContaining(String title);

}

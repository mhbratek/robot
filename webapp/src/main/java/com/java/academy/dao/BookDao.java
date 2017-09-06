package com.java.academy.dao;

import com.java.academy.model.Book;
import com.java.academy.model.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

	List<Book> findAll();
	
	Book getBookById(String bookId);

	Book getBookByTitleAndAuthorAndBookstore(String title, String Author, Bookstore bookstore);
	
	List<Book> getBooksByCategoryContaining(String category);

	List<Book> getBooksByAuthorContaining(String author);
	
	List<Book> getBooksByBookstoreNameContaining(String bookstore_name);

	List<Book> getBooksByTitleContaining(String title);

}

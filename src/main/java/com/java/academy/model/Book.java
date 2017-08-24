package com.java.academy.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "books")
public class Book extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 3678107792576131001L;
	@NotNull
	private String title;
	@NotNull
	private String author;
	private String category;
	private String promoDetails;
	@NotNull
	private BigDecimal price;
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bookstore_id", nullable = false)
	private Bookstore bookstore;

	public Book() {}
	
	public Book(String title, String author, String category, String promoDetails, BigDecimal price, Bookstore bookstore) {
		this.title = title;
		this.author = author;
		this.category = category;
		this.promoDetails = promoDetails;
		this.price = price;
		this.bookstore = bookstore;
	}
	
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public String getCategory() {
		return category;
	}
	public String getPromoDetails() {
		return promoDetails;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public Bookstore getBookstore() {
		return bookstore;
	}
}
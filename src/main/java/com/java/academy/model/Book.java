package com.java.academy.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
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
	private String genre;
	private String promoDetails;
	@NotNull
	private BigDecimal price;
	
	public Book() {}
	
	public Book(String title, String author, String genre, String promoDetails, BigDecimal price) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.promoDetails = promoDetails;
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public String getGenre() {
		return genre;
	}
	public String getPromoDetails() {
		return promoDetails;
	}
	public BigDecimal getPrice() {
		return price;
	}
}
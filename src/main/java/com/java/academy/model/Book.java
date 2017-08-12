package com.java.academy.model;

//TODO change to entity
public class Book {
	
	//TODO move to base entity file
	private long id;
	
	private String title;
	private String author;
	private String genre;
	private String promoDetails;
	private double price;//TODO change to BigDecimal
	
	public Book(long id, String title, String author, String genre, String promoDetails, double price) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.promoDetails = promoDetails;
		this.price = price;
	}
	
	public Long getId() {
		return id;
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
	public double getPrice() {
		return price;
	}
}
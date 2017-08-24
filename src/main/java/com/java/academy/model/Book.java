package com.java.academy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class Book extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 3678107792576131001L;
	@NotNull
	private String title;
	private String subtitle;
	@NotNull
	private String author;
	private String category;
	private String promoDetails;
	@NotNull
	private BigDecimal price;
	@Size(max=555)
	private String imgUrl;
	@NotNull
	@Size(max=555)
	private String url;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPromoDetails() {
		return promoDetails;
	}

	public void setPromoDetails(String promoDetails) {
		this.promoDetails = promoDetails;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Bookstore getBookstore() {
		return bookstore;
	}

	public void setBookstore(Bookstore bookstore) {
		this.bookstore = bookstore;
	}

	@Override
	public String toString() {
		return "Book{" +
				"title='" + title + '\'' +
				", subtitle='" + subtitle + '\'' +
				", author='" + author + '\'' +
				", category='" + category + '\'' +
				", promoDetails='" + promoDetails + '\'' +
				", price=" + price +
				", imgUrl='" + imgUrl + '\'' +
				", url='" + url + '\'' +
				", bookstore=" + bookstore +
				'}';
	}
}
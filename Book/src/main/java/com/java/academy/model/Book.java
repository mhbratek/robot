package com.java.academy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 3678107792576131001L;

	@NotNull
	@Size(max = 555)
	private String title;

	private String subtitle;

//	@NotNull
	private String author;

	@Size(max = 1000)
	private String category;

	private String promoDetails;

	@Size(max=555)
	private String imgUrl;

//	@NotNull
	@Size(max=555)
	private String url;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bookstore_id", nullable = false)
	private Bookstore bookstore;

	@OneToMany(mappedBy="book", fetch = FetchType.EAGER)
    private List<CollectionTime> collectedDates;

	public Book() {
	}
	
	public Book(String title, String author, String category, String promoDetails, Bookstore bookstore) {
		this.title = title;
		this.author = author;
		this.category = category;
		this.promoDetails = promoDetails;
		this.bookstore = bookstore;
		collectedDates = new ArrayList<>();
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

	public List<CollectionTime> getCollectedDates() {
		return collectedDates;
	}

	public void addCollectedDates(CollectionTime collectedTime) {
		this.collectedDates.add(collectedTime);
	}

	public void setCollectedDates(List<CollectionTime> collectedDates) {
		this.collectedDates = collectedDates;
	}

	@Override
	public String toString() {
		return "Book{" +
				"title='" + title + '\'' +
				", subtitle='" + subtitle + '\'' +
				", author='" + author + '\'' +
				", category='" + category + '\'' +
				", promoDetails='" + promoDetails + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				", url='" + url + '\'' +
				", bookstore=" + bookstore +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Book book = (Book) o;

		if (!title.equals(book.title)) return false;
		if (subtitle != null ? !subtitle.equals(book.subtitle) : book.subtitle != null) return false;
		if (!author.equals(book.author)) return false;
		if (!category.equals(book.category)) return false;
		return bookstore.equals(book.bookstore);
	}

	@Override
	public int hashCode() {
		int result = title.hashCode();
		result = 31 * result + (subtitle != null ? subtitle.hashCode() : 0);
		result = 31 * result + author.hashCode();
		result = 31 * result + category.hashCode();
		result = 31 * result + bookstore.hashCode();
		return result;
	}
}
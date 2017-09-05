package com.java.academy.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name="collected_dates")
public class CollectionTime extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="book_id")
    private Book book;
    private BigDecimal price;
    private Date date;

    public CollectionTime() {} //for Hibernate

    public CollectionTime(Book book, BigDecimal price, Date date) {
        this.book = book;
        this.price = price;
        this.date = date;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CollectionTime{" +
                "book=" + book +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}

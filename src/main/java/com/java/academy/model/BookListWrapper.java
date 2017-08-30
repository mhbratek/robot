package com.java.academy.model;

import java.util.ArrayList;
import java.util.List;

public class BookListWrapper {

    List<Book> books = new ArrayList<>();

    public BookListWrapper(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "BookListWrapper{" +
                "books=" + books +
                '}';
    }
}

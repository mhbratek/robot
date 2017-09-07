package com.java.academy.service.impl;


import com.java.academy.model.Book;

public enum Filter {

    TITLE {
        @Override
        boolean bookFits(Book book, String value) {
            return book.getTitle().toLowerCase().contains(value.toLowerCase());
        }
    },
    AUTHOR {
        @Override
        boolean bookFits(Book book, String value) {
            return book.getAuthor().toLowerCase().contains(value.toLowerCase());
        }
    },
    CATEGORY {
        @Override
        boolean bookFits(Book book, String value) {
            return book.getCategory().toLowerCase().contains(value.toLowerCase());
        }
    },
    BOOKSTORE {
        @Override
        boolean bookFits(Book book, String value) {
            return book.getBookstore().getName().toLowerCase().contains(value.toLowerCase());
        }
    },
    VERSION {
        @Override
        boolean bookFits(Book book, String value) {
            if(value.equals("0")) {
                return true;
            }
            return String.valueOf(book.getVersion()).equals(value);
        }

    };

    abstract boolean bookFits(Book book, String value);

    public Filter getFilter(String filter) {
        switch (filter) {
            case "title":
                return Filter.TITLE;
            case "author":
                return Filter.AUTHOR;
            case "category":
                return Filter.CATEGORY;
            case "bookstore":
                return Filter.BOOKSTORE;
            case "version":
                return Filter.VERSION;
        }
        return Filter.TITLE;

    }
}

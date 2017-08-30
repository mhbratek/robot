package com.java.academy.model.bookstore.googlebookstore;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author bratek
 * @since 29.08.17
 */
public class GoogleBookStoreTest {

    private static final int NUMBER_OF_COLLECTED_BOOKS = 440;

    @Test
    public void shouldReturn440Books() {
        GoogleBookStore bookStore = new GoogleBookStore();

        assertEquals(bookStore.collectBooksFromGoogle().size(), NUMBER_OF_COLLECTED_BOOKS);
    }
}
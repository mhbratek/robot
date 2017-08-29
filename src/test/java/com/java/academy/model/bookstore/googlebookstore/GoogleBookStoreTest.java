package com.java.academy.model.bookstore.googlebookstore;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author bratek
 * @since 29.08.17
 */
public class GoogleBookStoreTest {

    @Test
    public void shouldReturn440Books() {
        GoogleBookStore bookStore = new GoogleBookStore();

        assertEquals(bookStore.collectBooksFromGoogle().size(), 440);
    }
}
package com.java.academy.model;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class BookstoreTest {

    @DataProvider
    public Object[][] bookstoresData() {
        return new Object[][]{
                {"gandalf", "www.gandalf.com"},
                {"matras", "www.matras.com"}};
    }

    @Test (dataProvider = "bookstoresData")
    public void shouldSteBookstoreValues(String name, String url) {

        //given
        Bookstore bookstore = new Bookstore();

        //when
        bookstore.setName(name);
        bookstore.setUrl(url);

        //then
        assertEquals(bookstore.getUrl(), url);
        assertEquals(bookstore.getName(), name);
    }

}

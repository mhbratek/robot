package com.java.academy.model;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.util.Date;

import static org.testng.Assert.assertEquals;

@Test
public class CollectionTimeTest {

    @DataProvider
    public Object[][] initData() {
        return new Object[][]{
            {"title", BigDecimal.valueOf(25.99)}};
    }

    @Test (dataProvider = "initData")
    public void shouldCreateAppropriateCollectionTimeObject(String title, BigDecimal price){

        //given
        Book book = new Book();
        Date data = new Date();

        //when
        book.setTitle(title);
        book.setPrice(price);
        CollectionTime collectionTime = new CollectionTime(book, book.getPrice(), data);

        SoftAssert sa = new SoftAssert();
        sa.assertEquals(collectionTime.getBook(), book);
        sa.assertEquals(collectionTime.getDate(), data);
        sa.assertEquals(collectionTime.getPrice(), book.getPrice());
        sa.assertAll();
    }
    @Test (dataProvider = "initData")
    public void shouldSetCorrectValuesOfCollectionTime(String title, BigDecimal price) {
        //given
        Book book = new Book();
        Date data = new Date();
        CollectionTime collectionTime = new CollectionTime();

        //when
        book.setTitle(title);
        book.setPrice(price);
        collectionTime.setBook(book);
        collectionTime.setDate(data);
        collectionTime.setPrice(book.getPrice());

        //then
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(collectionTime.getBook(), book);
        sa.assertEquals(collectionTime.getDate(), data);
        sa.assertEquals(collectionTime.getPrice(), book.getPrice());
        sa.assertAll();

    }

    @Test (dataProvider = "initData")
    public void shouldReturnCorrectString(String title, BigDecimal price) {

        //given
        Book book = new Book();
        Date data = new Date();

        //when
        book.setTitle(title);
        book.setPrice(price);
        CollectionTime collectionTime = new CollectionTime(book, book.getPrice(), data);

        //then
        assertEquals(collectionTime.toString(), ("CollectionTime{" +
                "book=" + book +
                ", price=" + price +
                ", date=" + data +
                '}'));
    }

}

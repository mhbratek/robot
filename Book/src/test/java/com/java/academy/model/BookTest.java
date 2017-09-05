package com.java.academy.model;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;

@Test
public class BookTest {


    @DataProvider
    public Object[][] booksValues(){
        return new Object[][] {
            {"Pan Tadeusz", "Mickiewicz", "-", "-30%", BigDecimal.valueOf(100.00),
                    new Bookstore("Unknown", "http://nic.pl"), "www.image.jpg", "www.link.pl"},
            {"Potop", "Sienkiewicz", "-", "-25%", BigDecimal.valueOf(25.99),
                    new Bookstore("Unknown", "http://cos.pl"), "www.image.jpg", "www.link.pl"}
        };
    }


    @Test(dataProvider = "booksValues")
    public void shouldCreateBookWithAppropriateValues(String title, String author, String category, String discount,
                                                      BigDecimal price, Bookstore bookstore, String image, String link) {

        //given
        Book book = new Book(title, author, category, discount, price, bookstore);

        //when
        book.setImgUrl(image);
        book.setUrl(link);

        //then
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(book.getTitle(), title);
        sa.assertEquals(book.getAuthor(), author);
        sa.assertEquals(book.getCategory(), category);
        sa.assertEquals(book.getPromoDetails(), discount);
        sa.assertEquals(book.getPrice(), price);
        sa.assertEquals(book.getBookstore(), bookstore);
        sa.assertEquals(book.getImgUrl(), image);
        sa.assertEquals(book.getUrl(), link);
        sa.assertAll();
    }

    @Test(dataProvider = "booksValues")
    public void shouldCreateBookWithAppropriateBySetters(String title, String author, String category, String discount,
                                                      BigDecimal price, Bookstore bookstore, String image, String link) {
        //given
        Book book = new Book();

        //when
        book.setBookstore(bookstore);
        book.setAuthor(author);
        book.setPrice(price);
        book.setCategory(category);
        book.setTitle(title);
        book.setPromoDetails(discount);
        book.setImgUrl(image);
        book.setUrl(link);

        //then
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(book.getTitle(), title);
        sa.assertEquals(book.getAuthor(), author);
        sa.assertEquals(book.getCategory(), category);
        sa.assertEquals(book.getPromoDetails(), discount);
        sa.assertEquals(book.getPrice(), price);
        sa.assertEquals(book.getBookstore(), bookstore);
        sa.assertEquals(book.getImgUrl(), image);
        sa.assertEquals(book.getUrl(), link);
        sa.assertAll();
    }
}

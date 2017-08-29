package com.java.academy.model.bookstore.googlebookstore;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.java.academy.model.Book;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author bratek
 * @since 29.08.17
 */
public class BookMapperTest {

    private Item item;
    private VolumeInfo volumeInfo;
    private ImageLinks imageLinks;
    private SaleInfo saleInfo;
    private RetailPrice retailPrice;
    private ListPrice listPrice;


    @BeforeMethod
    public void setUp() {
        listPrice = new ListPrice();
        listPrice.setAmount(10d);

        retailPrice = new RetailPrice();
        retailPrice.setAmount(8d);

        imageLinks = new ImageLinks();
        imageLinks.setSmallThumbnail("www.google.pl");

        volumeInfo = new VolumeInfo();
        volumeInfo.setMainCategory("Category");
        volumeInfo.setImageLinks(imageLinks);
        volumeInfo.setAuthors(Arrays.asList("Janko Muzykant"));

        saleInfo = new SaleInfo();
        saleInfo.setRetailPrice(retailPrice);
        saleInfo.setListPrice(listPrice);

        item = new Item();
        item.setVolumeInfo(volumeInfo);
        item.setSaleInfo(saleInfo);
    }

    @Test
    public void shouldAssignGivenCategory () {
        Book book = new Book();
        BookMapper.assignCategory(item, book);

        assertEquals(book.getCategory(), item.getCategory());
    }

    @Test
    public void shouldAssignDefaultCategoryIfCategoryIsNull() {
        volumeInfo.setMainCategory(null);

        Book book = new Book();
        BookMapper.assignCategory(item, book);

        assertEquals(book.getCategory(), "Book");
    }

    @Test
    public void shouldAssignGivenURL() {
        Book book = new Book();
        BookMapper.assignImageURL(item, book);

        assertEquals(book.getImgUrl(), item.getImageLink());
    }

    @Test
    public void shouldAssignDefaultPriceIfPriceIsNull() {
        item.setSaleInfo(null);

        Book book = new Book();
        BookMapper.assignPrice(item, book);

        assertEquals(book.getPrice(), new BigDecimal(0));
    }

    @Test
    public void shouldAssignGivenPriceToBook() {
        Book book = new Book();
        BookMapper.assignPrice(item, book);

        assertEquals(book.getPrice(),  new BigDecimal(item.getPrice()));
    }

    @Test
    public void shouldAssignGivenPromotionDetails() {
        Book book = new Book();
        BookMapper.assignPromoDetails(item, book);

        assertEquals(book.getPromoDetails(), "20%");
    }

    @Test
    public void shouldReturnOneBookAfterMapping() {
        List<Item> items = new ArrayList<>();
        items.add(item);
        GoogleBook googleBook = new GoogleBook();
        googleBook.setItems(items);

        List<Book> expectedBooks = BookMapper.mapFromGoogleBookStore(googleBook);

        assertEquals(expectedBooks.size(), 1);
    }

    @Test
    public void shouldAssignDefaultAuthorIfAuthorIsUnknown() {
        volumeInfo.setAuthors(null);

        Book book = new Book();
        BookMapper.assignAuthors(item, book);

        assertEquals(book.getAuthor(), "Unknown");
    }

    @Test
    public void shouldAssignGivenAuthor() {
        Book book = new Book();
        BookMapper.assignAuthors(item, book);

        assertEquals(book.getAuthor(), item.getAuthors());
    }
}
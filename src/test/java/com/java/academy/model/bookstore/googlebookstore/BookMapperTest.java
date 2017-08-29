package com.java.academy.model.bookstore.googlebookstore;

import com.java.academy.model.Bookstore;
import org.testng.annotations.BeforeMethod;
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

    private static final String BOOKSTORE_NAME = "GooglePlay";
    private static final String BOOKSTORE_URL = "https://play.google.com/store/books?hl=en";
    private static final String IMAGE_URL = "www.google.pl";
    private static final String DEFAULT_CATEGORY = "Book";
    private static final String DEFAULT_AUTHOR = "Unknown";
    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private Item item;
    private VolumeInfo volumeInfo;

    @BeforeMethod
    public void setUp() {
        ListPrice listPrice = new ListPrice();
        listPrice.setAmount(10d);

        RetailPrice retailPrice = new RetailPrice();
        retailPrice.setAmount(8d);

        ImageLinks imageLinks = new ImageLinks();
        imageLinks.setSmallThumbnail(IMAGE_URL);

        volumeInfo = new VolumeInfo();
        volumeInfo.setMainCategory(DEFAULT_CATEGORY);
        volumeInfo.setImageLinks(imageLinks);
        volumeInfo.setAuthors(Arrays.asList(DEFAULT_AUTHOR));

        SaleInfo saleInfo = new SaleInfo();
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

        assertEquals(book.getCategory(), DEFAULT_CATEGORY);
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

        assertEquals(book.getPrice(), DEFAULT_PRICE);
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
        Bookstore bookstore = new Bookstore(BOOKSTORE_NAME, BOOKSTORE_URL);
        List<Item> items = new ArrayList<>();
        items.add(item);
        GoogleBook googleBook = new GoogleBook();
        googleBook.setItems(items);

        List<Book> expectedBooks = BookMapper.mapFromGoogleBookStore(googleBook, bookstore);

        assertEquals(expectedBooks.size(), items.size());
    }

    @Test
    public void shouldAssignDefaultAuthorIfAuthorIsUnknown() {
        volumeInfo.setAuthors(null);

        Book book = new Book();
        BookMapper.assignAuthors(item, book);

        assertEquals(book.getAuthor(), DEFAULT_AUTHOR);
    }

    @Test
    public void shouldAssignGivenAuthor() {
        Book book = new Book();
        BookMapper.assignAuthors(item, book);

        assertEquals(book.getAuthor(), item.getAuthors());
    }
}
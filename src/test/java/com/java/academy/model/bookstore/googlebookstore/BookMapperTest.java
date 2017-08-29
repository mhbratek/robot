package com.java.academy.model.bookstore.googlebookstore;

import org.testng.annotations.Test;
import com.java.academy.model.Book;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author bratek
 * @since 29.08.17
 */
public class BookMapperTest {

    @Test
    public void shouldAssignCategoryFromGoogleBookToBook () {
        GoogleBook googleBook = new GoogleBook();
        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.setMainCategory("Category");
        Item item = new Item();
        item.setVolumeInfo(volumeInfo);
        List<Item> items = new ArrayList<>();
        googleBook.setItems(items);
        items.add(item);

        Book book = new Book();
        BookMapper.assignCategory(item, book);

        assertEquals(book.getCategory(), item.getCategory());
    }

    @Test
    public void shouldAssignURLFromGoogleBookToBook() {
        GoogleBook googleBook = new GoogleBook();
        ImageLinks imageLinks = new ImageLinks();
        imageLinks.setSmallThumbnail("www.google.pl");

        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.setImageLinks(imageLinks);
        Item item = new Item();
        item.setVolumeInfo(volumeInfo);
        List<Item> items = new ArrayList<>();
        items.add(item);
        googleBook.setItems(items);

        Book book = new Book();
        BookMapper.assignImageURL(item, book);

        assertEquals(book.getImgUrl(), item.getImageLink());
    }

    @Test
    public void shouldAssignGivenPriceToBook() {
        GoogleBook googleBook = new GoogleBook();
        Item item = new Item();
        SaleInfo saleInfo = new SaleInfo();
        ListPrice listPrice = new ListPrice();
        listPrice.setAmount(10d);
        saleInfo.setListPrice(listPrice);
        item.setSaleInfo(saleInfo);
        List<Item> items = new ArrayList<>();
        items.add(item);
        googleBook.setItems(items);

        Book book = new Book();
        BookMapper.assignPrice(item, book);

        assertEquals(book.getPrice(),  new BigDecimal(item.getPrice()));
    }

    @Test
    public void shouldAssignGivenPromotionDetails() {
        GoogleBook googleBook = new GoogleBook();
        Item item = new Item();
        SaleInfo saleInfo = new SaleInfo();
        ListPrice listPrice = new ListPrice();
        listPrice.setAmount(10d);
        RetailPrice retailPrice = new RetailPrice();
        retailPrice.setAmount(8d);
        saleInfo.setListPrice(listPrice);
        saleInfo.setRetailPrice(retailPrice);
        item.setSaleInfo(saleInfo);
        List<Item> items = new ArrayList<>();
        items.add(item);
        googleBook.setItems(items);


        Book book = new Book();
        BookMapper.assignPromoDetails(item, book);

        assertEquals(book.getPromoDetails(), "20%");
    }
}
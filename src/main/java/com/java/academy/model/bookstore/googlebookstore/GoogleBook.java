package com.java.academy.model.bookstore.googlebookstore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author bratek
 * @since 24.08.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBook {

    private Long totalItems;
    private List<Item> items;

    public GoogleBook() {
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "GoogleBook{" +
                "totalItems=" + totalItems +
                ", items=" + items +
                '}';
    }
}

package com.java.academy.model.bookstore.googlebookstore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author bratek
 * @since 24.08.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleInfo {
    private ListPrice listPrice;
    private RetailPrice retailPrice;

    public SaleInfo() {
    }

    public ListPrice getListPrice() {
        return listPrice;
    }

    public void setListPrice(ListPrice listPrice) {
        this.listPrice = listPrice;
    }

    public RetailPrice getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(RetailPrice retailPrice) {
        this.retailPrice = retailPrice;
    }

}

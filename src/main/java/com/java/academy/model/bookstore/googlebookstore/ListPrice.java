package com.java.academy.model.bookstore.googlebookstore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author bratek
 * @since 24.08.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListPrice {
    private Double amount;

    public ListPrice() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


}

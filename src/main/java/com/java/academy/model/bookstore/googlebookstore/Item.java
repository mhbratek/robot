package com.java.academy.model.bookstore.googlebookstore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author bratek
 * @since 24.08.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private VolumeInfo volumeInfo;
    private SaleInfo saleInfo;

    public Item() {
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    public String getTitle() {
        return volumeInfo.getTitle();
    }

    public String getAuthors() {
        return volumeInfo.getAuthors().toString()
                .replaceAll("\\[","")
                .replaceAll("]","");
    }

    public Double getPrice() {
        return saleInfo.getListPrice().getAmount();
    }

    public String getLink() {
        return volumeInfo.getInfoLink();
    }

    public String getImageLink() {
        return volumeInfo.getImageLinks().getSmallThumbnail();
    }

    public String getCategory() {
        return volumeInfo.getMainCategory();
    }
}

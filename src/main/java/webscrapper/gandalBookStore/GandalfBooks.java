package webscrapper.gandalBookStore;

public class GandalfBooks {

    String bookTitle;
    String bookAuthor;
    Double bookPrice;
    String bookDiscount;
    String imageSrc;
    String shopLink;

    public GandalfBooks(String bookTitle, String bookAuthor, Double bookPrice, String bookDiscount, String imageSrc, String shopLink) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
        this.bookDiscount = bookDiscount;
        this.imageSrc = imageSrc;
        this.shopLink = shopLink;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public String getBookDiscount() {
        return bookDiscount;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getShopLink() {
        return shopLink;
    }

    @Override
    public String toString() {
        return "GandalfBooks: " + '\n' +
                "Title= " + bookTitle + '\n' +
                "Author= " + bookAuthor + '\n' +
                "Price= " + bookPrice+ '\n' +
                "Discount= " + bookDiscount + '\n' +
                "imageSrc= " + imageSrc + '\n' +
                "shopLink= " + shopLink + '\n';
    }
}

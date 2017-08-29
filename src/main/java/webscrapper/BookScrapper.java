package webscrapper;

public interface BookScrapper {
    String getBookAuthor();

    String getImageUrl();

    String getBookCategory();

    String getBookTitle();

    String getBookLink();

    Double getBookPrice();

    String getDiscount();
}

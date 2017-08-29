package com.java.academy.webScrappers.ravelo;


import com.java.academy.model.Bookstore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import static org.springframework.util.ResourceUtils.getFile;
import static org.testng.Assert.assertEquals;

@Test
public class RaveloScrapperTest {

    private final String resourcePage = "src/test/resources/ravelo_test.html";

    @DataProvider
    public Object[][] wrongProvider(){
        return new Object[][]{
                {"http://WRONG.url"},
                {"http://EVENWORSE.url"},
        };
    }

    @Test (dataProvider = "wrongProvider", expectedExceptions = UnknownHostException.class)
    public void shouldReturnExceptionIfUrlIncorrect(String url) throws IOException {
        //given
        RaveloScrapper raveloScrapper = new RaveloScrapper(url, new Bookstore());

        //then
        raveloScrapper.provideShopConnection(url);
    }

    @Test
    public void shouldReturnTotalNumberOfPages() throws IOException {
        //given
        RaveloScrapper raveloScrapper = new RaveloScrapper(resourcePage, new Bookstore());

        //when
        File in = getFile(resourcePage);
        Document document = Jsoup.parse(in, "UTF-8");

        //then
        assertEquals(raveloScrapper.getTotalNumberOfPages(document), 1);
    }

    @Test
    public void shouldReturnAppropriateNumberOfBookFromGivenPage() throws IOException {
        //given
        RaveloScrapper raveloScrapper = new RaveloScrapper(resourcePage, new Bookstore());

        //when
        File in = getFile(resourcePage);
        Document document = Jsoup.parse(in, "UTF-8");

        //then
        assertEquals(raveloScrapper.collectBooksFromSinglePage(document).size(), 23);
    }

}

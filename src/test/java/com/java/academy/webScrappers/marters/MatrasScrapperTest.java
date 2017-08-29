package com.java.academy.webScrappers.marters;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.springframework.util.ResourceUtils.getFile;
import static org.testng.Assert.assertEquals;

@Test
public class MatrasScrapperTest {

    public static final int BOOKS_ON_PAGE = 47;
    private final String resourcePage = "src/test/resources/matras_resource.html";

    @Test
    public void shouldInitializeBookstoreWithAppropriateValues() {
        MatrasScrapper matras = new MatrasScrapper();
        matras.initBookStore();

        assertEquals(matras.getBookStore().getName(), "Matras");
        assertEquals(matras.getBookStore().getUrl(), "http://www.matras.pl");
    }

    @Test
    public void shouldReturnAppropriateNumberOfBookFromGivenPage() throws IOException {
        //given
        MatrasScrapper matras = new MatrasScrapper();

        //when
        File in = getFile(resourcePage);
        Document document = Jsoup.parse(in, "UTF-8");

        //then
        assertEquals(matras.collectBooksFromSinglePage(document).size(), BOOKS_ON_PAGE);
    }

}

package com.java.academy.webScrappers.ravelo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.springframework.util.ResourceUtils.getFile;
import static org.testng.Assert.assertEquals;

@Test
public class RaveloBookProviderTest {

    private static final int NUM_OF_CATEGORIES = 11;
    private final String resourcePage = "/home/paul/IdeaProjects/robot/src/test/resources/outlet_ravelo.html";

    @Test
    public void shouldInitializeBookstoreWithAppropriateValues() {
        RaveloBookProvider raveloBookProvider = new RaveloBookProvider();
        raveloBookProvider.initializeBookStore();

        assertEquals(raveloBookProvider.getBookStore().getName(), "Ravelo");
        assertEquals(raveloBookProvider.getBookStore().getUrl(), "https://www.ravelo.pl");
    }

    @Test
    public void shouldReturnTotalNumberOfPages() throws IOException {
        //given
        RaveloBookProvider raveloBookProvider = new RaveloBookProvider();

        //when
        File in = getFile(resourcePage);
        Document document = Jsoup.parse(in, "UTF-8");

        //then
        assertEquals(raveloBookProvider.collectLinksToAllBooksCategory(document).size(), NUM_OF_CATEGORIES);
    }


}

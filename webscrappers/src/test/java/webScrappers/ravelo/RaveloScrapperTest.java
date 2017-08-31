package com.java.academy.webScrappers.ravelo;


import com.java.academy.webScrappers.DocumentLoader;
import com.java.academy.webScrappers.JSOUPLoader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.util.ResourceUtils.getFile;
import static org.testng.Assert.assertEquals;

@Test
public class RaveloScrapperTest {

    public static final int BOOKS_NUM = 23;
    private static final int NUM_OF_CATEGORIES = 1;

    private final String resourcePage = "src/test/resources/ravelo_test.html";
    private final String resourcePage1 = "src/test/resources/outlet_ravelo.html";


    @Test
    public void shouldInitializeBookstoreWithAppropriateValues() {
        RaveloScrapper raveloBookProvider = new RaveloScrapper(new JSOUPLoader());

        assertEquals(raveloBookProvider.getBookStore().getName(), "Ravelo");
        assertEquals(raveloBookProvider.getBookStore().getUrl(), "https://www.ravelo.pl");
    }

    @Test
    public void shouldReturnTotalNumberOfCategories() throws IOException {
        //given
        RaveloScrapper raveloBookProvider = new RaveloScrapper(new JSOUPLoader());

        //when
        File in = getFile(resourcePage1);
        Document document = Jsoup.parse(in, "UTF-8");

        //then
        assertEquals(raveloBookProvider.collectLinksToAllBooksCategory(document).size(), NUM_OF_CATEGORIES);
    }

    @Test
    public void shouldReturnAppropriateNumberOfBookFromGivenPage() throws IOException {
        //given
        DocumentLoader documentLoader = mock(JSOUPLoader.class);
        File in = getFile(resourcePage);

        //when
        when(documentLoader.loadHTMLDocument(anyString())).thenReturn(Jsoup.parse(in, "UTF-8"));
        RaveloScrapper raveloScrapper = new RaveloScrapper(documentLoader);

        //then

        assertEquals(raveloScrapper.prepareBookPackage(resourcePage).size(), BOOKS_NUM);
    }

}

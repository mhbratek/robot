package com.java.academy.webScrappers.marters;

import com.java.academy.webScrappers.DocumentLoader;
import com.java.academy.webScrappers.JSOUPLoader;
import org.jsoup.Jsoup;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.util.ResourceUtils.getFile;
import static org.testng.Assert.assertEquals;

@Test
public class MatrasScrapperTest {

    public static final int BOOKS_ON_PAGE = 27;
    private final String resourcePage = "src/test/resources/matras_resource.html";

    @Test
    public void shouldInitializeBookstoreWithAppropriateValues() {
        MatrasScrapper matras = new MatrasScrapper(new JSOUPLoader());
        matras.initBookStore();

        assertEquals(matras.getBookStore().getName(), "Matras");
        assertEquals(matras.getBookStore().getUrl(), "http://www.matras.pl");
    }

    @Test
    public void shouldReturnAppropriateNumberOfBookFromGivenPage() throws IOException {
        //given
        DocumentLoader documentLoader = mock(JSOUPLoader.class);
        File in = getFile(resourcePage);

        //when
        when(documentLoader.loadHTMLDocument(anyString())).thenReturn(Jsoup.parse(in, "UTF-8"));
        MatrasScrapper matras = new MatrasScrapper(documentLoader);

        //then
        assertEquals(matras.collectBooksFromMatras().size(), BOOKS_ON_PAGE);
    }

}

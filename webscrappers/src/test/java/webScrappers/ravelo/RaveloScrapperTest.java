package webScrappers.ravelo;


import org.jsoup.Jsoup;
import org.testng.annotations.Test;
import webScrappers.DocumentLoader;
import webScrappers.JSOUPLoader;
import webScrappers.mapper.BookMapper;
import webScrappers.mapper.BookMapperByStore;

import java.io.File;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.util.ResourceUtils.getFile;
import static org.testng.Assert.assertEquals;

@Test
public class RaveloScrapperTest {

    public static final int BOOKS_NUM = 5;

    private final String resourcePage = "src/test/resources/ravelo_test.html";

    @Test
    public void shouldInitializeBookstoreWithAppropriateValues() {
        RaveloScrapper raveloBookProvider = new RaveloScrapper(new JSOUPLoader());

        assertEquals(raveloBookProvider.getBookStore().getName(), "Ravelo");
        assertEquals(raveloBookProvider.getBookStore().getUrl(), "https://www.ravelo.pl");
    }

    @Test
    public void shouldReturnAppropriateNumberOfBookFromGivenPage() throws IOException {
        //given
        DocumentLoader documentLoader = mock(JSOUPLoader.class);
        File in = getFile(resourcePage);

        //when
        when(documentLoader.loadHTMLDocument(anyString())).thenReturn(Jsoup.parse(in, "UTF-8"));
        RaveloScrapper raveloScrapper = new RaveloScrapper(documentLoader);
        BookMapper mapper = new BookMapperByStore();

        //then

        assertEquals(mapper.collectBooksFromBookStore(raveloScrapper).size(), BOOKS_NUM);
    }

}

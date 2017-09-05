package webScrappers.taniaKsiazka;

import org.jsoup.Jsoup;
import org.testng.annotations.Test;
import webScrappers.DocumentLoader;
import webScrappers.JSOUPLoader;
import webScrappers.mapper.BookMapperByStore;

import java.io.File;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.util.ResourceUtils.getFile;
import static org.testng.Assert.assertEquals;

@Test
public class TaniaKsiazkaScrapperTest {

    private final String resourcePage = "src/test/resources/taniaksiazka.html";

    @Test
    public void shouldInitializeBookstoreWithAppropriateValues() {
        TaniaKsiazkaScrapper taniaKsiazkaScrapper = new TaniaKsiazkaScrapper(new JSOUPLoader());

        assertEquals(taniaKsiazkaScrapper.getBookStore().getName(), "TaniaKsiazka");
        assertEquals(taniaKsiazkaScrapper.getBookStore().getUrl(), "http://www.taniaksiazka.pl");
    }

    @Test
    public void shouldReturnAppropriateNumberOfBookFromGivenPage() throws IOException {
        //given
        DocumentLoader documentLoader = mock(JSOUPLoader.class);
        File in = getFile(resourcePage);

        //when
        when(documentLoader.loadHTMLDocument(anyString())).thenReturn(Jsoup.parse(in, "UTF-8"));
        TaniaKsiazkaScrapper taniaKsiazkaScrapper = new TaniaKsiazkaScrapper(documentLoader);
        BookMapperByStore bookMapper = new BookMapperByStore();

        //then
        assertEquals(bookMapper.collectBooksFromBookStore(taniaKsiazkaScrapper).size(), bookMapper.getTotalPageToCheck());
    }
}



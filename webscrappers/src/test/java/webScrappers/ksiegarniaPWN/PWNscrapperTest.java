package webScrappers.ksiegarniaPWN;

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
public class PWNscrapperTest {
    private final String resourcePage = "src/test/resources/pwn.html";

    @Test
    public void shouldInitializeBookstoreWithAppropriateValues() {
        PWNscrapper czytamyScrapper = new PWNscrapper(new JSOUPLoader());

        assertEquals(czytamyScrapper.getBookStore().getName(), "PWN");
        assertEquals(czytamyScrapper.getBookStore().getUrl(), "https://ksiegarnia.pwn.pl");
    }

    @Test
    public void shouldReturnAppropriateNumberOfBookFromGivenPage() throws IOException {
        //given
        DocumentLoader documentLoader = mock(JSOUPLoader.class);
        File in = getFile(resourcePage);

        //when
        when(documentLoader.loadHTMLDocument(anyString())).thenReturn(Jsoup.parse(in, "UTF-8"));
        PWNscrapper pwn = new PWNscrapper(documentLoader);
        BookMapperByStore bookMapper = new BookMapperByStore();

        //then
        assertEquals(bookMapper.collectBooksFromBookStore(pwn).size(), bookMapper.getTotalPageToCheck());
    }

}


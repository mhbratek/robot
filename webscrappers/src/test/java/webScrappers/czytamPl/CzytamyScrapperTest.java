package webScrappers.czytamPl;

import webScrappers.mapper.BookMapper;
import webScrappers.mapper.BookMapperByStore;
import webScrappers.DocumentLoader;
import webScrappers.JSOUPLoader;
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
public class CzytamyScrapperTest {
    public static final int BOOKS_ON_PAGE = 2; //for 5 times
    private final String resourcePage = "src/test/resources/Księgarnia internetowa Czytam.pl.html";

    @Test
    public void shouldInitializeBookstoreWithAppropriateValues() {
        CzytamyScrapper czytamyScrapper = new CzytamyScrapper(new JSOUPLoader());

        assertEquals(czytamyScrapper.getBookStore().getName(), "Czytamy");
        assertEquals(czytamyScrapper.getBookStore().getUrl(), "http://czytam.pl");
    }

    @Test
    public void shouldReturnAppropriateNumberOfBookFromGivenPage() throws IOException {
        //given
        DocumentLoader documentLoader = mock(JSOUPLoader.class);
        File in = getFile(resourcePage);

        //when
        when(documentLoader.loadHTMLDocument(anyString())).thenReturn(Jsoup.parse(in, "UTF-8"));
        CzytamyScrapper czytamyScrapper = new CzytamyScrapper(documentLoader);
        BookMapper bookMapper = new BookMapperByStore();

        //then
        assertEquals(bookMapper.collectBooksFromBookStore(czytamyScrapper).size(), BOOKS_ON_PAGE);
    }

}



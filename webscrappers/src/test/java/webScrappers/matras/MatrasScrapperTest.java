package webScrappers.matras;

import webScrappers.DocumentLoader;
import webScrappers.JSOUPLoader;
import org.jsoup.Jsoup;
import org.testng.annotations.Test;
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
public class MatrasScrapperTest {

    public static final int BOOKS_ON_PAGE = 5;
    private final String resourcePage = "src/test/resources/matras_resource.html";

    @Test
    public void shouldInitializeBookstoreWithAppropriateValues() {
        MatrasScrapper matras = new MatrasScrapper(new JSOUPLoader());

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
        BookMapper mapper = new BookMapperByStore(matras);

        //then
        assertEquals(mapper.collectBooksFromBookStore().size(), BOOKS_ON_PAGE);
    }

}

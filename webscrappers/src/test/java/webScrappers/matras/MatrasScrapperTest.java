package webScrappers.matras;

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
public class MatrasScrapperTest {

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
        BookMapperByStore mapper = new BookMapperByStore();

        //then
        assertEquals(mapper.collectBooksFromBookStore(matras).size(), mapper.getTotalPageToCheck());
    }

}

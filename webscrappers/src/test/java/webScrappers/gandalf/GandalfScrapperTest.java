package webScrappers.gandalf;

import org.jsoup.Jsoup;
import org.testng.annotations.Test;
import webScrappers.mapper.BookMapper;
import webScrappers.mapper.BookMapperByStore;
import webScrappers.DocumentLoader;
import webScrappers.JSOUPLoader;

import java.io.File;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.util.ResourceUtils.getFile;
import static org.testng.Assert.assertEquals;


@Test
public class GandalfScrapperTest {

    public static final int NUM_OF_BOOK = 2;
    private final String resourcePage = "src/test/resources/Promocje i wyprzedaże książek _ Gandalf.com.pl.html";

    @Test
    public void shouldInitializeBookstoreWithAppropriateValues() {
        GandalfScrapper gandalfScrapper = new GandalfScrapper(new JSOUPLoader());

        assertEquals(gandalfScrapper.getBookStore().getName(), "Gandalf");
        assertEquals(gandalfScrapper.getBookStore().getUrl(), "http://www.gandalf.com.pl");
    }

    @Test
    public void shouldReturnAppropriateNumberOfBookFromGivenPage() throws IOException {
        //given
        DocumentLoader documentLoader = mock(JSOUPLoader.class);
        File in = getFile(resourcePage);

        //when
        when(documentLoader.loadHTMLDocument(anyString())).thenReturn(Jsoup.parse(in, "UTF-8"));
        GandalfScrapper gandalfScrapper = new GandalfScrapper(documentLoader);
        BookMapper mapper = new BookMapperByStore();

        //then
        assertEquals(mapper.collectBooksFromBookStore(gandalfScrapper).size(), NUM_OF_BOOK);
    }
}

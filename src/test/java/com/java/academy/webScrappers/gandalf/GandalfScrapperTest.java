package com.java.academy.webScrappers.gandalf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.springframework.util.ResourceUtils.getFile;
import static org.testng.Assert.assertEquals;


@Test
public class GandalfScrapperTest {

    private final String resourcePage = "/home/paul/IdeaProjects/robot/src/test/resources/Promocje i wyprzedaże książek _ Gandalf.com.pl.html";

    @Test
    public void shouldInitializeBookstoreWithAppropriateValues() {
        GandalfScrapper gandalfScrapper = new GandalfScrapper();
        gandalfScrapper.initBookStore();

        assertEquals(gandalfScrapper.getBookStore().getName(), "Gandalf");
        assertEquals(gandalfScrapper.getBookStore().getUrl(), "http://www.gandalf.com.pl");
    }

    @Test
    public void shouldReturnAppropriateNumberOfBookFromGivenPage() throws IOException {
        //given
        GandalfScrapper gandalfScrapper = new GandalfScrapper();

        //when
        File in = getFile(resourcePage);
        Document document = Jsoup.parse(in, "UTF-8");

        //then
        assertEquals(gandalfScrapper.collectBooksFromSinglePage(document).size(), 38);
    }
}

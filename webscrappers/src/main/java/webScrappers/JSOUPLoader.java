package webScrappers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JSOUPLoader implements DocumentLoader{
    @Override
    public Document loadHTMLDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}

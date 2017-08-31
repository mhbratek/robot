package webScrappers;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface DocumentLoader {

    Document loadHTMLDocument(String url) throws IOException;
}

package webScrappers;


import org.testng.annotations.Test;

import java.io.IOException;


public class JSOUPLoaderTest {

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionIfWrongFormatOfUrl() throws IOException {

        //given
        DocumentLoader loader = new JSOUPLoader();

        //when
        String wrongLink = "notCorrect";

        //then
        loader.loadHTMLDocument(wrongLink);
    }



}

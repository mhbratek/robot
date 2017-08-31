package googlebookstore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author bratek
 * @since 24.08.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolumeInfo {
    private String title;
    private List<String> authors;
    private ImageLinks imageLinks;
    private String infoLink;
    private String mainCategory;

    public VolumeInfo() {

    }

    public String getTitle() {
        return title;
    }



    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }


    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getInfoLink() {
        return infoLink;
    }


    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }


}

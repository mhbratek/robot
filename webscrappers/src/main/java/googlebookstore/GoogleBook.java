package googlebookstore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author bratek
 * @since 24.08.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBook {

    private List<Item> items;

    public GoogleBook() {
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}

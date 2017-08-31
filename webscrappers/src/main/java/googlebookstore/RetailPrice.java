package googlebookstore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author bratek
 * @since 24.08.17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetailPrice {
    private Double amount;

    public RetailPrice() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}

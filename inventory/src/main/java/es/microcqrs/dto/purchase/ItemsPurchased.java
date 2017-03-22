package es.microcqrs.dto.purchase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * When the items have been purchased.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class ItemsPurchased {

    private final UUID id;
    private final Date sold;
    private final BigDecimal price;
    private final List<Item> items;

    @JsonCreator
    public ItemsPurchased(@JsonProperty("id") UUID id,
                          @JsonProperty("sold") Date sold,
                          @JsonProperty("price") BigDecimal price,
                          @JsonProperty("items") List<Item> items) {
        this.id = id;
        this.sold = sold;
        this.price = price;
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public UUID getId() {
        return id;
    }

    public Date getSold() {
        return sold;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

package es.microcqrs.dto.catalog;

import com.fasterxml.jackson.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Event thrown when there is a new product
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class ProductAdded {

    private final UUID id;
    private final String name;
    private final BigDecimal price;
    private final String picture;

    @JsonCreator
    public ProductAdded(@JsonProperty("id") UUID id,
                        @JsonProperty("name") String name,
                        @JsonProperty("price") BigDecimal price,
                        @JsonProperty("picture") String picture) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.picture = picture;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }
}

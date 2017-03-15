package es.microcqrs.dto.catalog;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Event thrown when there is a new product
 */
public class ProductAdded {

    private final UUID id;
    private final String name;
    private final BigDecimal price;
    private final String picture;

    public ProductAdded(UUID id, String name, BigDecimal price, String picture) {
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

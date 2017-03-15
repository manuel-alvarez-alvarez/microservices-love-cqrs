package es.microcqrs.dto.purchase;

import es.microcqrs.domain.Purchase;
import org.springframework.core.convert.ConversionService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * When the items have been purchased.
 */
public class ItemsPurchased {

    private final UUID id;
    private final Date sold;
    private final BigDecimal price;
    private final List<Item> items;

    public ItemsPurchased(UUID id, Date sold, BigDecimal price, List<Item> items) {
        this.id = id;
        this.sold = sold;
        this.price = price;
        this.items = items;
    }

    public ItemsPurchased(Purchase purchase, ConversionService conversionService) {
        this.id = purchase.getId();
        this.sold = purchase.getSold();
        this.price = purchase.getPrice();
        this.items = purchase.getItems().stream().map(it -> conversionService.convert(it, Item.class)).collect(Collectors.toList());
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

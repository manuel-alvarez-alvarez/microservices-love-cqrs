package es.microcqrs.aggregate;

import es.microcqrs.dto.catalog.ProductAdded;
import es.microcqrs.dto.purchase.*;
import es.microcqrs.dto.shipping.ShipmentCreated;
import es.microcqrs.dto.shipping.ShipmentDelivered;
import es.microcqrs.dto.shipping.ShipmentShipped;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Aggregate for the sales
 */
@EnableBinding(Processor.class)
public class Sales extends BaseAggregate {

    private Map<UUID, BigDecimal> productPrices = new LinkedHashMap<>();
    private Map<UUID, UUID> purchasesByShipment = new LinkedHashMap<>();

    public ItemsPurchased purchase(PurchaseItems request) {
        List<Item> finalItems = request.getItems()
                .stream()
                .map(it -> {
                    Item item = new Item();
                    item.setProduct(it.getProduct());
                    item.setPrice(productPrices.get(it.getProduct()));
                    item.setAmount(it.getAmount());
                    return item;
                })
                .collect(Collectors.toList());
        ItemsPurchased itemsPurchased = new ItemsPurchased(
                UUID.randomUUID(),
                new Date(),
                finalItems.stream().map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add),
                finalItems);
        return send(itemsPurchased);
    }

    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ProductAdded'")
    public void onProductAdded(ProductAdded productAdded) {
        this.productPrices.put(productAdded.getId(), productAdded.getPrice());
    }

    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ShipmentCreated'")
    protected void onShipmentCreated(ShipmentCreated shipmentCreated) {
        this.purchasesByShipment.put(shipmentCreated.getId(), shipmentCreated.getPurchase());
    }

    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ShipmentShipped'")
    protected void onShipmentShipped(ShipmentShipped shipmentShipped) {
        PurchaseShipped purchaseShipped = new PurchaseShipped(this.purchasesByShipment.get(shipmentShipped.getId()), new Date());
        send(purchaseShipped);
    }

    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ShipmentDelivered'")
    protected void onShipmentDelivered(ShipmentDelivered shipmentDelivered) {
        PurchaseDelivered purchaseDelivered = new PurchaseDelivered(this.purchasesByShipment.get(shipmentDelivered.getId()), new Date());
        send(purchaseDelivered);
    }
}

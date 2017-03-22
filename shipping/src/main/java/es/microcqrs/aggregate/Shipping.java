package es.microcqrs.aggregate;

import es.microcqrs.dto.purchase.ItemsPurchased;
import es.microcqrs.dto.purchase.PurchaseCanceled;
import es.microcqrs.dto.shipping.*;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Aggregate for the shipping
 */
@EnableBinding(Processor.class)
public class Shipping extends BaseAggregate {

    private Map<UUID, UUID> shipmentsByPurchase = new LinkedHashMap<>();

    public ShipmentShipped ship(ShipShipment request) {
        ShipmentShipped shipmentShipped = new ShipmentShipped(request.getId(), new Date());
        return send(shipmentShipped);
    }

    public ShipmentDelivered deliver(DeliverShipment request) {
        ShipmentDelivered shipmentShipped = new ShipmentDelivered(request.getId(), new Date());
        return send(shipmentShipped);
    }

    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ShipmentCreated'")
    private void onShipmentCreated(ShipmentCreated shipmentCreated) {
        this.shipmentsByPurchase.put(shipmentCreated.getPurchase(), shipmentCreated.getId());
    }

    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ItemsPurchased'")
    private void onItemsPurchased(ItemsPurchased itemsPurchased) {
        ShipmentCreated shipmentCreated = new ShipmentCreated(UUID.randomUUID(), itemsPurchased.getId(), new Date());
        send(shipmentCreated);
    }

    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='PurchaseCanceled'")
    private void onPurchaseCanceled(PurchaseCanceled purchaseCanceled) {
        ShipmentCanceled shipmentCanceled = new ShipmentCanceled(
                this.shipmentsByPurchase.get(purchaseCanceled.getId()), purchaseCanceled.getReason(), new Date());
        send(shipmentCanceled);
    }

}

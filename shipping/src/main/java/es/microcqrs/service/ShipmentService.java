package es.microcqrs.service;

import es.microcqrs.domain.Shipment;
import es.microcqrs.dto.shipping.ShipmentCanceled;
import es.microcqrs.dto.shipping.ShipmentCreated;
import es.microcqrs.dto.shipping.ShipmentDelivered;
import es.microcqrs.dto.shipping.ShipmentShipped;
import es.microcqrs.repository.ShipmentRepository;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Read model service for the shipments
 */@EnableBinding(Processor.class)

public class ShipmentService {

    @Resource
    private ShipmentRepository shipmentRepository;

    @Transactional(readOnly = true)
    public List<Shipment> getShipments() {
        return shipmentRepository.findAll();
    }

    @Transactional
    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ShipmentCreated'")
    protected void onShipmentCreated(ShipmentCreated shipmentCreated) {
        Shipment shipment = new Shipment();
        shipment.setId(shipmentCreated.getId());
        shipment.setPurchase(shipmentCreated.getPurchase());
        shipment.setStatus(Shipment.Status.NEW);
        shipment.setTimestamp(shipmentCreated.getTimestamp());
        shipmentRepository.save(shipment);
    }

    @Transactional
    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ShipmentShipped'")
    protected void onShipmentShipped(ShipmentShipped shipmentShipped) {
        Shipment shipment = shipmentRepository.findOne(shipmentShipped.getId());
        shipment.setStatus(Shipment.Status.SHIPPED);
        shipment.setTimestamp(shipmentShipped.getTimestamp());
        shipmentRepository.save(shipment);
    }

    @Transactional
    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ShipmentDelivered'")
    protected void onShipmentDelivered(ShipmentDelivered shipmentDelivered) {
        Shipment shipment = shipmentRepository.findOne(shipmentDelivered.getId());
        shipment.setStatus(Shipment.Status.DELIVERED);
        shipment.setTimestamp(shipmentDelivered.getTimestamp());
        shipmentRepository.save(shipment);
    }

    @Transactional
    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ShipmentCanceled'")
    protected void onShipmentCanceled(ShipmentCanceled shipmentCanceled) {
        Shipment shipment = shipmentRepository.findOne(shipmentCanceled.getId());
        shipment.setStatus(Shipment.Status.CANCELED);
        shipment.setReason(shipmentCanceled.getReason());
        shipment.setTimestamp(shipmentCanceled.getTimestamp());
        shipmentRepository.save(shipment);
    }
}

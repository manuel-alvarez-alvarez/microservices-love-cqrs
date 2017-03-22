package es.microcqrs.controller;

import es.microcqrs.aggregate.Shipping;
import es.microcqrs.domain.Shipment;
import es.microcqrs.dto.shipping.DeliverShipment;
import es.microcqrs.dto.shipping.ShipShipment;
import es.microcqrs.dto.shipping.ShipmentDelivered;
import es.microcqrs.dto.shipping.ShipmentShipped;
import es.microcqrs.service.ShipmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller for the API
 */
@RestController
@RequestMapping(value = "/api/shipping")
@CrossOrigin(origins = "*")
public class ShippingController {

    @Resource
    private ShipmentService shipmentService;

    @Resource
    private Shipping shipping;

    @GetMapping
    public List<Shipment> shipping() {
        return shipmentService.getShipments();
    }

    @PostMapping("/ship")
    public ShipmentShipped ship(@RequestBody ShipShipment request) {
        return shipping.ship(request);
    }

    @PostMapping("/deliver")
    public ShipmentDelivered deliver(@RequestBody DeliverShipment request) {
        return shipping.deliver(request);
    }

}

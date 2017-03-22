package es.microcqrs.controller;

import es.microcqrs.aggregate.Sales;
import es.microcqrs.domain.Purchase;
import es.microcqrs.dto.purchase.ItemsPurchased;
import es.microcqrs.dto.purchase.PurchaseItems;
import es.microcqrs.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller for the API
 */
@RestController
@RequestMapping(value = "/api/sales")
@CrossOrigin(origins = "*")
public class SalesController {

    @Resource
    private PurchaseService purchaseService;

    @Resource
    private Sales sales;

    @GetMapping
    public List<Purchase> sales() {
        return purchaseService.getPurchases();
    }

    @PostMapping
    public ItemsPurchased purchase(@RequestBody PurchaseItems request) {
        return sales.purchase(request);
    }

}

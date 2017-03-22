package es.microcqrs.service;

import es.microcqrs.domain.Purchase;
import es.microcqrs.domain.PurchaseItem;
import es.microcqrs.dto.purchase.ItemsPurchased;
import es.microcqrs.dto.purchase.PurchaseCanceled;
import es.microcqrs.dto.purchase.PurchaseDelivered;
import es.microcqrs.dto.purchase.PurchaseShipped;
import es.microcqrs.repository.PurchaseRepository;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Read model service for the purchases
 */
@EnableBinding(Processor.class)
public class PurchaseService {

    @Resource
    private PurchaseRepository purchaseRepository;

    @Resource
    private ConversionService conversionService;

    @Transactional(readOnly = true)
    public List<Purchase> getPurchases() {
        return purchaseRepository.findAll();
    }

    @Transactional
    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='ItemsPurchased'")
    protected void onItemsPurchased(ItemsPurchased itemsPurchased) {
        Purchase purchase = new Purchase();
        purchase.setId(itemsPurchased.getId());
        purchase.setStatus(Purchase.Status.NEW);
        purchase.setItems(itemsPurchased.getItems().stream().map(it -> conversionService.convert(it, PurchaseItem.class)).collect(Collectors.toList()));
        purchase.getItems().forEach(it -> {
            it.setId(UUID.randomUUID());
            it.setPurchase(purchase);
            it.setPrice(it.getPrice().multiply(new BigDecimal(it.getAmount())));
        });
        purchase.setSold(itemsPurchased.getSold());
        purchase.setPrice(itemsPurchased.getPrice());
        purchaseRepository.save(purchase);
    }

    @Transactional
    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='PurchaseCanceled'")
    protected void onPurchaseCanceled(PurchaseCanceled purchaseCanceled) {
        Purchase purchase = purchaseRepository.findOne(purchaseCanceled.getId());
        purchase.setStatus(Purchase.Status.CANCELED);
        purchase.setReason(purchaseCanceled.getReason());
        purchaseRepository.save(purchase);
    }

    @Transactional
    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='PurchaseShipped'")
    protected void onPurchaseShipped(PurchaseShipped purchaseShipped) {
        Purchase purchase = purchaseRepository.findOne(purchaseShipped.getId());
        purchase.setStatus(Purchase.Status.SHIPPED);
        purchaseRepository.save(purchase);
    }

    @Transactional
    @StreamListener(value = Processor.INPUT, condition = "headers['type']=='PurchaseDelivered'")
    protected void onPurchaseDelivered(PurchaseDelivered purchaseDelivered) {
        Purchase purchase = purchaseRepository.findOne(purchaseDelivered.getId());
        purchase.setStatus(Purchase.Status.DELIVERED);
        purchaseRepository.save(purchase);
    }
}

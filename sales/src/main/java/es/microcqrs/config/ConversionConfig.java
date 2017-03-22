package es.microcqrs.config;

import es.microcqrs.domain.PurchaseItem;
import es.microcqrs.dto.purchase.Item;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Configuration for the conversion
 */
@Configuration
public class ConversionConfig {

    @Bean
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(new HashSet<>(Arrays.asList(
                new ToPurchaseItem(),
                new ToItem()
        )));
        return bean;
    }

    private static class ToPurchaseItem implements Converter<Item, PurchaseItem> {

        @Override
        public PurchaseItem convert(Item source) {
            PurchaseItem result = new PurchaseItem();
            result.setProduct(source.getProduct());
            result.setAmount(source.getAmount());
            result.setPrice(source.getPrice().multiply(new BigDecimal(source.getAmount())));
            return result;
        }
    }

    private static class ToItem implements Converter<PurchaseItem, Item> {

        @Override
        public Item convert(PurchaseItem source) {
            Item result = new Item();
            result.setProduct(source.getProduct());
            result.setAmount(source.getAmount());
            result.setPrice(source.getPrice());
            return result;
        }
    }
}

package es.microcqrs;

import es.microcqrs.dto.catalog.ProductAdded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Bootstrap class to initialize all the products.
 */
@Component
public class Bootstrap {

    private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    @Resource
    private Processor stream;

    @Value(value = "classpath:db/changelog/product.csv")
    private org.springframework.core.io.Resource products;

    @Async
    public void createProducts() throws IOException {
        try {
            Thread.sleep(5000L); // take it easy man!
            LOG.info("Adding products to the catalog");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(products.getInputStream()))) {
                String theLine;
                while ((theLine = reader.readLine()) != null) {
                    if (!theLine.startsWith("id")) {
                        String[] parts = theLine.split(";");
                        this.send(
                                new ProductAdded(
                                        UUID.fromString(parts[0]),
                                        parts[1],
                                        new BigDecimal(parts[3]),
                                        parts[2])
                        );
                    }
                }
            }
        } catch (InterruptedException e) {
            // ignore it
        }
    }

    private void send(Object item) {
        stream.output().send(MessageBuilder.withPayload(item).setHeader("type", item.getClass().getSimpleName()).build());
    }

}

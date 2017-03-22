package es.microcqrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

/**
 * Main entry point for the monolith
 */
@SpringBootApplication
@EnableWebMvc
@EnableAsync
@EnableDiscoveryClient
public class CatalogApp {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = SpringApplication.run(CatalogApp.class, args);
        applicationContext.getBean(Bootstrap.class).createProducts();
    }
}

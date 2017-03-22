package es.microcqrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

/**
 * Main entry point for the monolith
 */
@SpringBootApplication
@EnableWebMvc
@EnableDiscoveryClient
public class ShippingApp {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ShippingApp.class, args);
    }
}

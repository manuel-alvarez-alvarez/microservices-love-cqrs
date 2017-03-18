package es.microcqrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.io.IOException;

/**
 * Main entry point for the monolith
 */
@SpringBootApplication
@EnableWebFlux
@EnableAsync
public class Monolith {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = SpringApplication.run(Monolith.class, args);
        applicationContext.getBean(Bootstrap.class).createProducts();
    }
}

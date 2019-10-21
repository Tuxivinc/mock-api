package org.api.mock.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The Configuration.
 */
@ComponentScan("org.api.mock")
@EnableScheduling
@SpringBootApplication
public class Configuration {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Configuration.class, args);
    }

}

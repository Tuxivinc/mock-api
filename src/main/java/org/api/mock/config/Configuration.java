package org.api.mock.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The Configuration.
 */
@ComponentScan("org.api.mock")
@EnableScheduling
@SpringBootApplication
public class Configuration {

    @Value("${starting.latency.ms}")
    private String latencyStartingUp;

    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Configuration.class, args);
    }

    @Bean
    public void sleep() throws InterruptedException {
        LOG.info("Apply Latency of starting Up {}ms", latencyStartingUp);
        Thread.sleep(Long.valueOf(latencyStartingUp));
    }

}

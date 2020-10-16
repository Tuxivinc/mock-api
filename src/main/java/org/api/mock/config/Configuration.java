package org.api.mock.config;

import org.api.mock.services.multicast.MulticastReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * The Configuration.
 */
@ComponentScan("org.api.mock")
@EnableScheduling
@SpringBootApplication
@EnableAsync
public class Configuration {

    @Value("${starting.latency.ms}")
    private String latencyStartingUp;

    @Resource
    private TaskExecutor taskExecutor;
    @Resource
    private MulticastReceiver multicastReceiver;

    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    public static void main(String[] args) {
        SpringApplication.run(Configuration.class, args);
    }

    @Bean
    public void init() throws InterruptedException {
        LOG.info("Apply Latency of starting Up {}ms", latencyStartingUp);
        Thread.sleep(Long.valueOf(latencyStartingUp));
        LOG.info("End simul Latency of starting Up ");
    }

    @PostConstruct
    public void atStartup() {
        LOG.info("Run Multicast Receiver thread");
        taskExecutor.execute(multicastReceiver);
        LOG.info("Run all sync");
        multicastReceiver.initialiserSync();
    }

}

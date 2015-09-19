package org.demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Batch application that demonstrates reading from XML file
 * and writing to H2 database.
 **/
@SpringBootApplication
@EnableBatchProcessing
public class BatchDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchDemoApplication.class, args);
    }


}

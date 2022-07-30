package org.webscraper.productScraper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class GroceryScraperApplication {

    public static void main(String[] args) {
        System.setProperty("jdk.httpclient.allowRestrictedHeaders", "Host");
        SpringApplication.run(GroceryScraperApplication.class, args);
    }

}

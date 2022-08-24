package org.webscraper.productScraper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.webscraper.productScraper.entities.QuantityAndUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
@Slf4j
@EnableScheduling
@EnableConfigurationProperties
@EnableAsync
public class GroceryScraperApplication {

    public static void main(String[] args) {
        System.setProperty("jdk.httpclient.allowRestrictedHeaders", "Host");



        var a = QuantityAndUnit.getPriceAndUnit("Ciocolata Kinder 8 bucati, 100 g");

        SpringApplication.run(GroceryScraperApplication.class, args);
    }

}

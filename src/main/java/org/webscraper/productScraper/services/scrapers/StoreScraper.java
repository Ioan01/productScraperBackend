package org.webscraper.productScraper.services.scrapers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.webscraper.productScraper.entities.Category;
import org.webscraper.productScraper.entities.Manufacturer;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.repos.CategoryRepo;
import org.webscraper.productScraper.repos.ManufacturerRepo;
import org.webscraper.productScraper.repos.ProductRepo;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Map;

@Slf4j
public abstract class StoreScraper {

    protected Map<String, HttpRequest> httpRequests;

    protected CategoryRepo categoryRepo;

    protected ManufacturerRepo manufacturerRepo;

    protected ProductRepo productRepo;

    public StoreScraper(CategoryRepo categoryRepo, ManufacturerRepo manufacturerRepo, ProductRepo productRepo, HttpClient httpClient) {
        this.categoryRepo = categoryRepo;
        this.manufacturerRepo = manufacturerRepo;
        this.productRepo = productRepo;
        this.httpClient = httpClient;
    }

    protected HttpClient httpClient;

    protected abstract Map<String, HttpRequest> generateRequests();

    protected abstract String getCategoryBody(HttpRequest request) throws IOException, InterruptedException;
    protected abstract ArrayList<Product> interpretBody(String requestBody,String category);

    protected void fetchStoreData(){

        log.info("Starting Mega Image import");

        httpRequests.forEach(((category, uri) ->{
            log.info("Pulling " + category +" from " + uri);
            categoryRepo.findByCategoryName(category).orElseGet(()->categoryRepo.save(new Category(category)));

            String requestBody = null;
            try {
                requestBody = getCategoryBody(uri);
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
            //log.info(requestBody);
            var products = interpretBody(requestBody,category);

            //log.info(products.toString());

            //products.forEach((product -> productRepo.save(product)));
        } ));
    }

    @PostConstruct
    public void onStartup()
    {
        httpRequests = generateRequests();
        fetchStoreData();
    }

    @Async
    @Scheduled(cron = "@daily")
    public void onSchedule()
    {
        fetchStoreData();
    }

}

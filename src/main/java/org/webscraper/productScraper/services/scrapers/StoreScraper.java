package org.webscraper.productScraper.services.scrapers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.webscraper.productScraper.entities.Category;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.entities.ProductPriceNode;
import org.webscraper.productScraper.entities.Store;
import org.webscraper.productScraper.repos.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public abstract class StoreScraper {

    protected final String[] categoryByCode = {
            "Fructe/Legume",
            "Lactate/Oua",
            "Mezeluri/Carne",
            "Congelate",
            "Paine/MicDejun",
            "Dulciuri/Snacks",
            "Condimente",
//            "Bautura/Tutun"
    };

    protected final StoreRepo storeRepo;
    protected final CategoryRepo categoryRepo;
    protected Store storeReference;
    protected Map<String, HttpRequest> httpRequests;
    protected ManufacturerRepo manufacturerRepo;
    protected ProductRepo productRepo;
    protected ProductPriceRepo priceRepo;
    protected ObjectMapper objectMapper;
    protected HttpClient httpClient;

    public StoreScraper(Store store, StoreRepo storeRepo, CategoryRepo categoryRepo, ManufacturerRepo manufacturerRepo, ProductRepo productRepo, HttpClient httpClient, ObjectMapper objectMapper, ProductPriceRepo priceRepo) {

        this.categoryRepo = categoryRepo;
        this.manufacturerRepo = manufacturerRepo;
        this.productRepo = productRepo;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.priceRepo = priceRepo;
        this.storeRepo = storeRepo;

        this.storeReference = storeRepo.findByStoreName(store.getStoreName())
                .orElseGet(() -> storeRepo.save(store));
    }


    // generate a map containing a http endpoint for each category
    protected abstract Map<String, HttpRequest> generateRequests();


    // fetch the body from the category endpoint
    protected abstract String getCategoryBody(HttpRequest request) throws IOException, InterruptedException;


    //deserialize the body into a product list
    protected abstract ArrayList<Product> interpretBody(String requestBody, String category) throws JsonProcessingException;


    protected void fetchStoreData() {

        log.info("Starting Mega Image import");


        // http endpoints are generated during startup to know where data for each category can be found
        httpRequests.forEach(((category, uri) -> {
            log.info("Pulling " + category + " from " + uri);

            // find category, otherwise save it
            var _category = categoryRepo.findByCategoryName(category)
                    .orElseGet(() -> categoryRepo.save(new Category(category)));

            String requestBody = null;
            try {
                // fetch data
                requestBody = getCategoryBody(uri);
                // interpret data into products
                var products = interpretBody(requestBody, category);

                // save each product and their price today
                products.forEach((product -> {

                    // try to find the product, otherwise we will save it

                    AtomicBoolean found = new AtomicBoolean(true);

                    Product _product = productRepo.getProductByProductNameAndStore(product.getProductName(), storeReference)
                            .orElseGet(() -> {
                                found.set(false);
                                return productRepo.save(product
                                        .withCategory(_category)
                                        .withStore(storeReference));
                            });

                    // update product
                    if (found.get()) {
                        productRepo.save(_product);
                        log.info("Updated product " + _product.getProductName());
                    }

                    // if product price node exists for product at this date do nothing
                    // otherwise, save
                    priceRepo.getByProductAndDate(_product, new Date(System.currentTimeMillis()))
                            .orElseGet(() -> priceRepo.save(new ProductPriceNode(_product)));
                }));

            } catch (Exception e) {
                log.error("Error fetching category " + category, e);
            }
        }));
    }

    @Async
    @PostConstruct
    public void onStartup() {
        httpRequests = generateRequests();
        fetchStoreData();
    }

    @Async
    @Scheduled(cron = "@daily")
    public void onSchedule() {
        fetchStoreData();
    }

}

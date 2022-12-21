package org.webscraper.productScraper.services.scrapers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.webscraper.productScraper.entities.*;
import org.webscraper.productScraper.repos.*;
import org.webscraper.productScraper.services.EntityService.ProductService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@EnableAsync
public abstract class StoreScraper<Body> {

    protected final String[] categories = {
            "Fructe/Legume",
            "Lactate/Oua",
            "Mezeluri/Carne",
            "Congelate/Conserve",
            "Paine/MicDejun",
            "Dulciuri/Snacks",
            "Condimente",
            "Apa/sucuri"
    };
    protected final StoreRepo storeRepo;
    protected final CategoryRepo categoryRepo;
    private final String storeName;
    protected Store storeReference;
    protected Map<String, HttpRequest[]> httpRequestsByCategory;
    protected ManufacturerRepo manufacturerRepo;
    protected ProductService productService;
    protected ProductPriceRepo priceRepo;
    protected ImageRepository imageRepository;
    protected ObjectMapper objectMapper;
    protected HttpClient httpClient;

    public StoreScraper(Store store, ImageRepository imageRepository, StoreRepo storeRepo, CategoryRepo categoryRepo, ManufacturerRepo manufacturerRepo, ProductService productService, HttpClient httpClient, ObjectMapper objectMapper, ProductPriceRepo priceRepo) {

        this.storeName = store.getStoreName();
        this.productService = productService;
        this.imageRepository = imageRepository;
        this.categoryRepo = categoryRepo;
        this.manufacturerRepo = manufacturerRepo;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.priceRepo = priceRepo;
        this.storeRepo = storeRepo;

        this.storeReference = storeRepo.findByStoreName(store.getStoreName())
                .orElseGet(() -> {
                    fetchImage(store);
                    return storeRepo.save(store);
                });
    }


    // generate a map containing a http endpoint for each category
    protected abstract Map<String, HttpRequest[]> generateRequests();


    // fetch the body from the category endpoint
    protected abstract Body getCategoryBody(HttpRequest request) throws IOException, InterruptedException;


    //deserialize the body into a product list
    protected abstract ArrayList<Product> interpretBody(Body requestBody, String category) throws JsonProcessingException;


    @Async
    public void fetchStoreData() {

        log.info("Starting import from " + storeName);


        // http endpoints are generated during startup to know where data for each category can be found
        httpRequestsByCategory.forEach(((category, requests) -> {
            new Thread(() -> {

                // find category, otherwise save it
                var _category = categoryRepo.findByCategoryName(category)
                        .orElseGet(() -> categoryRepo.save(new Category(category)));

                // for each request in category fetch
                Arrays.stream(requests).forEach((request) -> {
                    try {
                        Body requestBody = getCategoryBody(request);
                        // interpret data into products
                        var products = interpretBody(requestBody, category);

                        // save each product and their price today
                        products.forEach((product -> {

                            // try to find the product, otherwise we will save it

                            var _product = productService.addOrUpdateProduct(product, storeReference);

                            // if product price node exists for product at this date do nothing
                            // otherwise, save
                            priceRepo.getByProductAndDate(_product, new Date(System.currentTimeMillis()))
                                    .orElseGet(() -> priceRepo.save(new ProductPriceNode(_product)));
                        }));

                    } catch (Exception e) {
                        log.error("Error fetching category " + category, e);
                    }
                });
                // fetch data

            }).start();
        }));

        log.info("Updated prices from " + storeName);
    }

    @Async
    @PostConstruct
    public void onStartup() {
        httpRequestsByCategory = generateRequests();

        new Thread(this::fetchStoreData).start();
    }

    @Async
    @Scheduled(cron = "@daily")
    public void onSchedule() {
        fetchStoreData();
    }

    public void fetchImage(IVisualEntity entity) {
        try {
            var response = httpClient.send(HttpRequest.newBuilder(entity.getImageUri()).GET().build(), HttpResponse.BodyHandlers.ofByteArray());
            entity.setImage(imageRepository.save(new Image().withImage(response.body())));
        } catch (Exception e) {
            log.warn("Failed fetching " + entity.getImageUri());
        }

    }

}

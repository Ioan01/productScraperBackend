package org.webscraper.productScraper.services.scrapers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webscraper.productScraper.dto.MIProductsDTO;
import org.webscraper.productScraper.entities.Manufacturer;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.entities.Store;
import org.webscraper.productScraper.pojo.MIProductPOJO;
import org.webscraper.productScraper.repos.*;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MIScraper extends StoreScraper {

    private static final String webSite = "https://www.mega-image.ro/";

    private static final String iconLocation = "https://d1lqpgkqcok0l.cloudfront.net/static/next/images/logo_header_mega-image.svg?buildNumber=2dc12ee09edf2d5f61eef78a721bc485b0f8108243178aef4532bcd17470ae4e";

    private final String baseUrl = "https://api.mega-image.ro/" +
            "?operationName=GetCategoryProductSearch&variables=" + URLEncoder.encode(
            "{\"lang\":\"ro\",\"searchQuery\":\"\",\"category\":\"CATEGORY\",\"pageNumber\":0,\"pageSize\":10,\"filterFlag\":true}") +
            "&extensions=+" +
            URLEncoder.encode("{\"persistedQuery\":{\"version\":1,\"sha256Hash\":\"d2240b8505967fc091dacc9aa24ff71e86740067b9b36630ea857b0b29172c81\"}}");


    public MIScraper(StoreRepo storeRepo, CategoryRepo categoryRepo, ManufacturerRepo manufacturerRepo, ProductRepo productRepo, HttpClient httpClient, ObjectMapper objectMapper, ProductPriceRepo priceRepo) {
        super(new Store().withStoreName("Mega Image").withWebSite(URI.create(webSite)).withIcon(URI.create(iconLocation)),
                storeRepo, categoryRepo, manufacturerRepo, productRepo, httpClient, objectMapper, priceRepo);
    }


    @Override
    protected Map<String, HttpRequest> generateRequests() {

        Map<String, HttpRequest> requestMap = new HashMap<>();

        int index = 1;

        for (var category : categoryByCode) {
            var request = HttpRequest.newBuilder(URI.create(baseUrl.replace("CATEGORY", "00" + index))).GET().header("Host", "api.mega-image.ro").build();
            requestMap.put(category, request);
            index++;
        }

        return requestMap;
    }

    @Override
    protected String getCategoryBody(HttpRequest request) throws IOException, InterruptedException {
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();

    }


    @Override
    protected ArrayList<Product> interpretBody(String requestBody, String category) throws JsonProcessingException {
        ArrayList<Product> products = new ArrayList<>();
        MIProductsDTO miProductsDTO = objectMapper.readValue(requestBody, MIProductsDTO.class);
        Map<String, Object> cps = (Map<String, Object>) miProductsDTO.getData().get("categoryProductSearch");
        var productPOJOS = objectMapper.convertValue(cps.get("products"), MIProductPOJO[].class);

        Arrays.stream(productPOJOS).forEach((pojo) -> {

            var manufacturer = manufacturerRepo.findByManufacturerName(pojo.getManufacturerName())
                    .orElseGet(() -> manufacturerRepo.save(new Manufacturer().withManufacturerName(pojo.getManufacturerName())));

            products.add(pojo.toProduct().withManufacturer(manufacturer));
        });

        return products;
    }


}

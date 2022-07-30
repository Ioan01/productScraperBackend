package org.webscraper.productScraper.services.scrapers;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Store;
import org.springframework.stereotype.Service;
import org.webscraper.productScraper.WebToolsProvider;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.repos.CategoryRepo;
import org.webscraper.productScraper.repos.ManufacturerRepo;
import org.webscraper.productScraper.repos.ProductRepo;

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
public class MegaImageStoreScraper extends StoreScraper {




    private final String baseUrl = "https://api.mega-image.ro/" +
            "?operationName=GetCategoryProductSearch&variables=" + URLEncoder.encode(
            "{\"lang\":\"ro\",\"searchQuery\":\"\",\"category\":\"CATEGORY\",\"pageNumber\":0,\"pageSize\":2000,\"filterFlag\":true}") +
            "&extensions=+" +
            URLEncoder.encode("{\"persistedQuery\":{\"version\":1,\"sha256Hash\":\"d2240b8505967fc091dacc9aa24ff71e86740067b9b36630ea857b0b29172c81\"}}");

    private final String[] categoryByCode = {
            "Fructe/Legume",
            "Lactate/Oua",
            "Mezeluri/Carne",
            "Congelate",
            "Paine/MicDejun",
            "Dulciuri/Snacks",
            "Condimente",
//            "Bautura/Tutun"
    };



    public MegaImageStoreScraper(CategoryRepo categoryRepo, ManufacturerRepo manufacturerRepo, ProductRepo productRepo, HttpClient httpClient) {
        super(categoryRepo, manufacturerRepo, productRepo, httpClient);
    }

    @Override
    protected Map<String, HttpRequest> generateRequests() {

        Map<String,HttpRequest> requestMap = new HashMap<>();

        int index = 1;

        for (var category:categoryByCode) {
            var request = HttpRequest.newBuilder(URI.create(baseUrl.replace("CATEGORY", "00" + index))).GET().header("Host","api.mega-image.ro").build();
            requestMap.put(category,request );
            index++;
        }

        return requestMap;
    }

    @Override
    protected String getCategoryBody(HttpRequest request) throws IOException, InterruptedException {
        var response =  httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();

    }


    @Override
    protected ArrayList<Product> interpretBody(String requestBody, String category) {
        return null;
    }


}

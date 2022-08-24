package org.webscraper.productScraper.services.scrapers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.webscraper.productScraper.entities.Manufacturer;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.entities.QuantityAndUnit;
import org.webscraper.productScraper.entities.Store;
import org.webscraper.productScraper.repos.*;
import org.webscraper.productScraper.services.EntityService.ProductService;

import javax.print.Doc;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AuchanScraper extends StoreScraper<Document> {

    private static final String iconLocation = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1e/Auchan_logo.svg/2560px-Auchan_logo.svg.png";
    private static final String website = "https://www.auchan.ro";
    private static final String fructeLegume = "https://www.auchan.ro/store/Fructe-si-Legume/c/u2t0c0s0000";
    private static final String lactate = "https://www.auchan.ro/store/Lactate/c/u4t1c0s0000";
    private static final String oua = "https://www.auchan.ro/store/Oua/c/u4t2c0s0000";
    private static final String carne = "https://www.auchan.ro/store/Carne/c/u3t1c0s0000";
    private static final String peste = "https://www.auchan.ro/store/Pescarie/c/u3t2c0s0000";
    private static final String mezeluri = "https://www.auchan.ro/store/Mezeluri/c/u3t3c0s0000";
    private static final String paine = "https://www.auchan.ro/store/Paine/c/u5t1c0s0000";
    private static final String cereale = "https://www.auchan.ro/store/Cereale/c/u6t8c0s0000";
    private static final String conserve = "https://www.auchan.ro/store/Conserve/c/u6t9c0s0000";
    private static final String prajituri = "https://www.auchan.ro/store/Cofetarie/c/u5t3c0s0000";
    private static final String dulciuri = "https://www.auchan.ro/store/Dulciuri/c/u6t2c0s0000";
    private static final String ciocolata = "https://www.auchan.ro/store/Ciocolata/c/u6t3c0s0000";
    private static final String snacks = "https://www.auchan.ro/store/Alimente-sarate/c/u6t4c0s0000";
    private static final String condimente = "https://www.auchan.ro/store/Condimente-si-sosuri/c/u6t7c0s0000";
    private static final String apa = "https://www.auchan.ro/store/Apa/c/u7t1c0s0000";
    private static final String sucuri = "https://www.auchan.ro/store/Sucuri-si-bauturi-racoritoare/c/u7t2c0s0000";
    private static final String query = "?page=0&view=grid&pageSize=1000";

    public AuchanScraper(StoreRepo storeRepo, ImageRepository imageRepository, CategoryRepo categoryRepo, ManufacturerRepo manufacturerRepo, ProductService productRepo, HttpClient httpClient, ObjectMapper objectMapper, ProductPriceRepo priceRepo) {
        super(new Store().withStoreName("Auchan")
                        .withWebSite(URI.create(website))
                        .withImageUri(URI.create(iconLocation))

                , imageRepository, storeRepo, categoryRepo, manufacturerRepo, productRepo, httpClient, objectMapper, priceRepo);
    }

    @Override
    protected Map<String, HttpRequest[]> generateRequests() {

        Map<String, HttpRequest[]> requestMap = new HashMap<>();

        requestMap.put(categories[0], new HttpRequest[]{
                HttpRequest.newBuilder(URI.create(fructeLegume+query)).GET().build()
        });

        requestMap.put(categories[1], new HttpRequest[]{
                HttpRequest.newBuilder(URI.create(lactate+query)).GET().build(),
                HttpRequest.newBuilder(URI.create(oua+query)).GET().build(),
        });

        requestMap.put(categories[2], new HttpRequest[]{
                HttpRequest.newBuilder(URI.create(carne+query)).GET().build(),
                HttpRequest.newBuilder(URI.create(peste+query)).GET().build(),
                HttpRequest.newBuilder(URI.create(mezeluri+query)).GET().build(),
        });

        requestMap.put(categories[3], new HttpRequest[]{
                HttpRequest.newBuilder(URI.create(conserve+query)).GET().build(),
        });

        requestMap.put(categories[4], new HttpRequest[]{
                HttpRequest.newBuilder(URI.create(paine+query)).GET().build(),
                HttpRequest.newBuilder(URI.create(cereale+query)).GET().build(),
        });

        requestMap.put(categories[5], new HttpRequest[]{
                HttpRequest.newBuilder(URI.create(dulciuri+query)).GET().build(),
                HttpRequest.newBuilder(URI.create(prajituri+query)).GET().build(),
                HttpRequest.newBuilder(URI.create(snacks+query)).GET().build(),
                HttpRequest.newBuilder(URI.create(ciocolata+query)).GET().build(),
        });

        requestMap.put(categories[6], new HttpRequest[]{
                HttpRequest.newBuilder(URI.create(condimente+query)).GET().build(),
        });

        requestMap.put(categories[7], new HttpRequest[]{
                HttpRequest.newBuilder(URI.create(apa+query)).GET().build(),
                HttpRequest.newBuilder(URI.create(sucuri+query)).GET().build(),
        });

        return requestMap;
    }

    @Override
    protected Document getCategoryBody(HttpRequest request) throws IOException, InterruptedException {

        Document doc = Jsoup.connect(request.uri().toString()).get();
        var elements = doc.select(".productGridItem [title]").select(".productMainLink");
        var prices = elements.select(".bottom-part");
        return doc;
    }

    @Override
    protected ArrayList<Product> interpretBody(Document requestBody, String category) throws JsonProcessingException {

        var array = new ArrayList<Product>();

        var products = requestBody.select(".productGridItem");

        products.forEach((product)->{

            var image = website + product.select(".lazy").attr("data-src");

            var data = product.select(".js-gtm");
            var manufacturer = data.attr("data-gtm-brand");
            var price = data.attr("data-gtm-price");
            var name = data.attr("data-gtm-name");

            var quantAndUnit = QuantityAndUnit.getPriceAndUnit(name);

            var manufacturerRef = manufacturerRepo.findByManufacturerName(manufacturer)
                    .orElseGet(() -> manufacturerRepo.save(new Manufacturer().withManufacturerName(manufacturer)));

            array.add(new Product().withProductName(name).withManufacturer(manufacturerRef).withImageUri(URI.create(image))
                    .withPricePerUnit(BigDecimal.valueOf(Double.parseDouble(price)).divide(quantAndUnit.getQuantity(),4, RoundingMode.HALF_EVEN))
                    .withUnit(quantAndUnit.getUnit()));

        });

        return array;
    }
}

package org.webscraper.productScraper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webscraper.productScraper.services.scrapers.MIScraper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RestController
public class TestController {


    @Autowired
    HttpClient httpClient;
    MIScraper MIScraper;

    public TestController(MIScraper MIScraper) {
        this.MIScraper = MIScraper;
    }

    @GetMapping("/pullMega")
    public ResponseEntity<String> pullMega() throws IOException, InterruptedException {
        //megaImageStoreScraper.fetchStoreData();

        HttpResponse<String> response =
                httpClient.send(HttpRequest.newBuilder(URI.create("https://api.mega-image.ro/?operationName=GetCategoryProductSearch&variables=%7B%22lang%22%3A%22ro%22%2C%22searchQuery%22%3A%22%22%2C%22category%22%3A%22001%22%2C%22pageNumber%22%3A0%2C%22pageSize%22%3A20%2C%22filterFlag%22%3Atrue%7D&extensions=%7B%22persistedQuery%22%3A%7B%22version%22%3A1%2C%22sha256Hash%22%3A%22d2240b8505967fc091dacc9aa24ff71e86740067b9b36630ea857b0b29172c81%22%7D%7D")).build(), HttpResponse.BodyHandlers.ofString());


        return new ResponseEntity<>(response.body(), HttpStatus.OK);
    }

}

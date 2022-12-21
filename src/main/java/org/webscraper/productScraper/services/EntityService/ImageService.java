package org.webscraper.productScraper.services.EntityService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.stereotype.Service;
import org.webscraper.productScraper.entities.Image;
import org.webscraper.productScraper.repos.ImageRepository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class ImageService{

    ImageRepository repository;
    private final HttpClient httpClient;


    public ImageService(ImageRepository repository, HttpClient httpClient) {
        this.repository = repository;
        this.httpClient = httpClient;
    }

    Image createFromUri(URI imageUri)
    {
        try {
            var response = httpClient.send(HttpRequest.newBuilder(imageUri).GET().build(), HttpResponse.BodyHandlers.ofByteArray());
            return repository.save(new Image().withImage(response.body()));
        } catch (Exception e) {
            log.warn("Failed fetching " + imageUri);
            return null;
        }
    }

}

package org.webscraper.productScraper.services.EntityService;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.repos.ProductRepo;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class ProductService {

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    ProductRepo repo;

    @Async
    public CompletableFuture<Product> save(Product entity) {
        return repo.save(entity);
    }

    public CompletableFuture<ArrayList<Product>> getByName(String name, Pageable pageable) {
        return null;
    }

    public CompletableFuture<ArrayList<Product>> getAll(String name, Pageable pageable) {
        return null;
    }

    public void delete(String name) {

    }
}

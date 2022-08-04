package org.webscraper.productScraper.services.EntityService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.repos.ProductRepo;
import org.webscraper.productScraper.services.IEntityService;

import java.util.Optional;

@Service
public class ProductService implements IEntityService<Product> {

    ProductRepo repo;

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    @Override
    public Product save(Product entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(String name) {
        var product = getByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        repo.delete(product);
    }

    @Override
    public Optional<Product> getByName(String name) {
        return null;
    }

    @Override
    public Optional<Product> getByNameLike(String name) {
        return Optional.empty();
    }

    @Override
    public Product getElseCreate(String name) {
        return null;
    }

    @Override
    public Product getLikeElseCreate(String name) {
        return null;
    }
}

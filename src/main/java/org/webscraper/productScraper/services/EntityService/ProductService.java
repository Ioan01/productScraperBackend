package org.webscraper.productScraper.services.EntityService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.entities.Store;
import org.webscraper.productScraper.repos.ProductRepo;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    ProductRepo repo;

    ImageService imageService;

    public ProductService(ProductRepo repo, ImageService imageService) {
        this.repo = repo;
        this.imageService = imageService;
    }


    public Product addOrUpdateProduct(Product product, Store store) {
        // try to find product, otherwise get its image and save it in db
        var _product = repo.getProductByProductNameAndStore(product.getProductName(), store).orElseGet(
                () -> {
                    product.setImage(imageService.createFromUri(product.getImageUri()));
                    return repo.save(product);
                }
        );


        // update price if necessary
        if (!Objects.equals(_product.getPricePerUnit(), product.getPricePerUnit()))
            _product.setPricePerUnit(product.getPricePerUnit());


        return repo.save(_product);
    }

    public Page<Product> getByName(String name, Pageable pageable) {


        return repo.getAllByProductName(name,pageable);

    }

    public CompletableFuture<ArrayList<Product>> getAll(String name, Pageable pageable) {
        return null;
    }

}

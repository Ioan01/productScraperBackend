package org.webscraper.productScraper.repos;

import org.springframework.data.repository.Repository;
import org.webscraper.productScraper.entities.Product;

public interface ProductRepo extends Repository<Product, Long> {

    Product save(Product product);

}

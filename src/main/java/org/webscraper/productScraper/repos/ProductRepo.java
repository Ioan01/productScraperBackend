package org.webscraper.productScraper.repos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.entities.Store;

import java.util.ArrayList;
import java.util.Optional;

public interface ProductRepo extends Repository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    Product save(Product product);


    Optional<Product> getProductByProductNameAndStore(String productName, Store store);

    void delete(Product product);

    ArrayList<Product> getAllByProductName(String productName, Pageable pageable);
}

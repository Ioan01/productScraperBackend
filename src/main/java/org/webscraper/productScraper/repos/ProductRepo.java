package org.webscraper.productScraper.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.entities.Store;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    Optional<Product> getProductByProductNameAndStore(String productName, Store store);

    void delete(Product product);

    @Query(value = "SELECT p FROM Product p WHERE LOWER( p.productName) LIKE concat(?1,'%')")
    Page<Product> getAllByProductName(String productName,Pageable pageable);
}

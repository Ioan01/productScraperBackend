package org.webscraper.productScraper.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.entities.ProductPriceNode;

import java.sql.Date;
import java.util.Optional;

public interface ProductPriceRepo extends JpaRepository<ProductPriceNode, Long> {



    Optional<ProductPriceNode> getAllByProductProductName(String productName);

    Optional<ProductPriceNode> getByProductAndDate(Product product, Date date);

}

package org.webscraper.productScraper.repos;

import org.springframework.data.repository.Repository;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.entities.ProductPriceNode;

import java.sql.Date;
import java.util.Optional;

public interface ProductPriceRepo extends Repository<ProductPriceNode, Long> {
    ProductPriceNode save(ProductPriceNode node);



    Optional<ProductPriceNode> getAllByProductProductName(String productName);

    Optional<ProductPriceNode> getByProductAndDate(Product product, Date date);

}

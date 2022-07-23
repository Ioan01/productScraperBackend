package org.webscraper.productScraper.repos;

import org.springframework.data.repository.Repository;
import org.webscraper.productScraper.entities.Store;

public interface StoreRepo extends Repository<Store, Long> {
    Store save(Store store);
}

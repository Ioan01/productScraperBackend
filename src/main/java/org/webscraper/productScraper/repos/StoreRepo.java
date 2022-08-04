package org.webscraper.productScraper.repos;

import org.springframework.data.repository.Repository;
import org.webscraper.productScraper.entities.Store;

import java.util.Optional;

public interface StoreRepo extends Repository<Store, Long> {
    Store save(Store store);

    Optional<Store> findByStoreName(String storeName);


}

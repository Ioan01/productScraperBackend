package org.webscraper.productScraper.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.webscraper.productScraper.entities.Store;

import java.util.Optional;

public interface StoreRepo extends JpaRepository<Store, Long> {

    Optional<Store> findByStoreName(String storeName);


}

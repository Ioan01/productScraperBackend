package org.webscraper.productScraper.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.webscraper.productScraper.entities.Manufacturer;

import java.util.Optional;

public interface ManufacturerRepo extends JpaRepository<Manufacturer, Long> {

    Optional<Manufacturer> findByManufacturerName(String manufacturerName);
}


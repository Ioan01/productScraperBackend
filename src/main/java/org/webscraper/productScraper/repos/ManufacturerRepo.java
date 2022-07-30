package org.webscraper.productScraper.repos;

import org.springframework.data.repository.Repository;
import org.webscraper.productScraper.entities.Manufacturer;

import java.util.Optional;

public interface ManufacturerRepo extends Repository<Manufacturer, Long> {
    Manufacturer save(Manufacturer manufacturer);

    Optional<Manufacturer> findManufacturerByManufacturerName(String manufacturerName);
}


package org.webscraper.productScraper.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.webscraper.productScraper.entities.Image;

public interface ImageRepository extends JpaRepository<Image,Long> {


}

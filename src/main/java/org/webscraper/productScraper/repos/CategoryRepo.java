package org.webscraper.productScraper.repos;

import org.springframework.data.repository.Repository;
import org.webscraper.productScraper.entities.Category;

import java.util.Optional;

public interface CategoryRepo extends Repository<Category, Long> {

    Category save(Category category);

    Optional<Category> findByCategoryName(String categoryName);
}

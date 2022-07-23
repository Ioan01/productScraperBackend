package org.webscraper.groceryscraper.repos;

import org.springframework.data.repository.Repository;
import org.webscraper.groceryscraper.entities.Product;

import java.util.Optional;

public interface ProductRepo extends Repository<Long, Product> {



}

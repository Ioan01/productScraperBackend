package org.webscraper.productScraper.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.webscraper.productScraper.entities.CurrencyEntry;

import java.util.ArrayList;
import java.util.Date;

public interface CurrencyRepository extends JpaRepository<CurrencyEntry, Long> {


    ArrayList<CurrencyEntry> getCurrencyEntriesByDateAfter(Date date);

}

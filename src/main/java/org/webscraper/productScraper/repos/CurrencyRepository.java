package org.webscraper.productScraper.repos;

import org.springframework.data.repository.Repository;
import org.webscraper.productScraper.entities.CurrencyEntry;

import java.util.ArrayList;
import java.util.Date;

public interface CurrencyRepository extends Repository<CurrencyEntry, Long> {

    CurrencyEntry save(CurrencyEntry entry);

    ArrayList<CurrencyEntry> getCurrencyEntriesByDateAfter(Date date);

}

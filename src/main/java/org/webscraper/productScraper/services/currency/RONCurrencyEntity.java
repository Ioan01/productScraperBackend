package org.webscraper.productScraper.services.currency;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;


@Data
public class RONCurrencyEntity {
    private String date;

    private Map<String, BigDecimal> ron;
}

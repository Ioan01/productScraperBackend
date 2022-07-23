package org.webscraper.productScraper.services;

import org.springframework.stereotype.Controller;
import org.webscraper.productScraper.services.scheduled.CurrencyService;

@Controller
public class Test {
    CurrencyService service;

    public Test(CurrencyService service) {
        this.service = service;
    }
}

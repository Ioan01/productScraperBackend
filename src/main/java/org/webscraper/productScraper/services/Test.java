package org.webscraper.groceryscraper.services;

import org.springframework.stereotype.Controller;
import org.webscraper.groceryscraper.services.scheduled.CurrencyService;

@Controller
public class Test {
    CurrencyService service;

    public Test(CurrencyService service) {
        this.service = service;
    }
}

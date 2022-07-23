package org.webscraper.productScraper.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

public interface StoreScraper {


    public void fetchCurrencies();

    @PostConstruct
    public void onStartup();

    @Async
    @Scheduled(cron = "@daily")
    public void onSchedule();

}

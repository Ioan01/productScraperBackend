package org.webscraper.productScraper.services.scrapers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

@Slf4j
public abstract class StoreScraper {


    public abstract void fetchStoreData();

    @PostConstruct
    public void onStartup()
    {
        fetchStoreData();
    }

    @Async
    @Scheduled(cron = "@daily")
    public void onSchedule()
    {
        fetchStoreData();
    }

}

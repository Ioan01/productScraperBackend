package org.webscraper.productScraper.services.scrapers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@EnableAsync
public class AsyncTest {


    @Async
    @PostConstruct
    public void test()
    {
        log.info("test");
    }
}

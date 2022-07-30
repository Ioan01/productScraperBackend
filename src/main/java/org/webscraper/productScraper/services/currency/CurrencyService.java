package org.webscraper.productScraper.services.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class CurrencyService {

    private final String apiUrl
            = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/ron.json";

    private RONCurrencyEntity ronCurrency;

    HttpClient httpClient;

    ObjectMapper objectMapper;

    public CurrencyService(HttpClient httpClient, ObjectMapper objectMapper) throws IOException, InterruptedException {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void onStartup() throws IOException, InterruptedException {
        fetchCurrencies();
    }

    @Async
    @Scheduled(cron = "@daily")
    public void onSchedule() throws IOException, InterruptedException {
        fetchCurrencies();
    }
    public void fetchCurrencies() throws IOException, InterruptedException {
        log.info("Updating currencies...");

        HttpRequest request = HttpRequest.newBuilder().uri(
                URI.create(apiUrl)
        ).GET().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        ronCurrency = objectMapper.readValue(response.body(),RONCurrencyEntity.class);

        log.info(String.format("EUR : %s RON\t USD : %s RON ",
                BigDecimal.valueOf(1.0).divide(toEUR(),2, RoundingMode.HALF_EVEN).toString(),
                BigDecimal.valueOf(1.0).divide(toUSD(),2, RoundingMode.HALF_EVEN).toString() ));
    }

    public BigDecimal toUSD()
    {
        return ronCurrency.getRon().get("usd");
    }

    public BigDecimal toEUR()
    {
        return ronCurrency.getRon().get("eur");
    }


}

package org.webscraper.productScraper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;

@Component
public class WebToolsProvider {

    @Bean
    public ObjectMapper getGsonBuilder()
    {
        return new ObjectMapper();
    }

    @Bean
    public HttpClient getHttpClient()
    {
        return HttpClient.newHttpClient();
    }
}

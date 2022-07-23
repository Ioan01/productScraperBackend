package org.webscraper.groceryscraper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
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

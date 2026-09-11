package com.cristobalrivas.matchservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class HttpClientConfig {

    @Bean
    RestClient.Builder riotRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    RestClient playerRestClient(
            @Value("${player.service.url}") String playerServiceUrl) {
        return RestClient.builder()
                .baseUrl(playerServiceUrl)
                .build();
    }
}

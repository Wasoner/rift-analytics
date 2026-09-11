package com.cristobalrivas.playerservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RiotClientConfig {

    @Bean
    RestClient riotRestClient(
            @Value("${riot.api.americas-url}") String baseUrl,
            @Value("${riot.api.key}") String apiKey) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("X-Riot-Token", apiKey)
                .build();
    }
}

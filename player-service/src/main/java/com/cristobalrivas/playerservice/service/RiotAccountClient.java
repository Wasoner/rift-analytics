package com.cristobalrivas.playerservice.service;

import com.cristobalrivas.playerservice.dto.RiotAccountDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RiotAccountClient {

    private final RestClient restClient;

    public RiotAccountClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public RiotAccountDto getAccountByRiotId(String gameName, String tagLine) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
                        .build(gameName, tagLine))
                .retrieve()
                .body(RiotAccountDto.class);
    }
}

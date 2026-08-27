package com.cristobalrivas.riftanalytics.service;

import com.cristobalrivas.riftanalytics.dto.RiotAccountDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RiotService {

    private final RestClient riotRestClient;

    public RiotService(RestClient riotRestClient) {
        this.riotRestClient = riotRestClient;
    }

    public RiotAccountDto getAccountByRiotId(String gameName, String tagLine) {
        return riotRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
                        .build(gameName, tagLine))
                .retrieve()
                .body(RiotAccountDto.class);
    }
}

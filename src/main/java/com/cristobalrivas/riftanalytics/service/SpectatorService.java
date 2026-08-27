package com.cristobalrivas.riftanalytics.service;

import com.cristobalrivas.riftanalytics.dto.CurrentGameInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Service
public class SpectatorService {

    private final String platformUrlTemplate;
    private final String apiKey;

    public SpectatorService(
            @Value("${riot.api.platform-url-template}") String platformUrlTemplate,
            @Value("${riot.api.key}") String apiKey) {
        this.platformUrlTemplate = platformUrlTemplate;
        this.apiKey = apiKey;
    }

    public Optional<CurrentGameInfoDto> findActiveGame(String region, String encryptedPuuid) {
        if (!region.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("La región contiene caracteres no válidos");
        }

        RestClient client = RestClient.builder()
                .baseUrl(platformUrlTemplate.replace("{region}", region))
                .defaultHeader("X-Riot-Token", apiKey)
                .build();

        try {
            CurrentGameInfoDto game = client.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/lol/spectator/v5/active-games/by-summoner/{encryptedPUUID}")
                            .build(encryptedPuuid))
                    .retrieve()
                    .body(CurrentGameInfoDto.class);

            return Optional.ofNullable(game);
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }
}

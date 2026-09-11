package com.cristobalrivas.matchservice.client;

import com.cristobalrivas.matchservice.dto.CurrentGameInfoDto;
import com.cristobalrivas.matchservice.error.RiotBadResponseException;
import com.cristobalrivas.matchservice.error.RiotUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Optional;

@Component
public class RiotSpectatorClient {

    private final String platformUrlTemplate;
    private final String apiKey;
    private final RestClient.Builder restClientBuilder;

    public RiotSpectatorClient(
            RestClient.Builder restClientBuilder,
            @Value("${riot.api.platform-url-template}") String platformUrlTemplate,
            @Value("${riot.api.key}") String apiKey) {
        this.restClientBuilder = restClientBuilder;
        this.platformUrlTemplate = platformUrlTemplate;
        this.apiKey = apiKey;
    }

    public Optional<CurrentGameInfoDto> findActiveGame(String region, String puuid) {
        RestClient client = restClientBuilder.clone()
                .baseUrl(platformUrlTemplate.replace("{region}", region))
                .defaultHeader("X-Riot-Token", apiKey)
                .build();

        try {
            CurrentGameInfoDto game = client.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/lol/spectator/v5/active-games/by-summoner/{puuid}")
                            .build(puuid))
                    .retrieve()
                    .body(CurrentGameInfoDto.class);
            return Optional.ofNullable(game);
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        } catch (ResourceAccessException exception) {
            throw new RiotUnavailableException();
        } catch (RestClientResponseException exception) {
            throw new RiotBadResponseException();
        }
    }
}

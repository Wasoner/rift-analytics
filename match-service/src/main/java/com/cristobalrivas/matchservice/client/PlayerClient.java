package com.cristobalrivas.matchservice.client;

import com.cristobalrivas.matchservice.dto.RiotAccountDto;
import com.cristobalrivas.matchservice.error.PlayerNotFoundException;
import com.cristobalrivas.matchservice.error.PlayerServiceBadResponseException;
import com.cristobalrivas.matchservice.error.PlayerServiceUnavailableException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class PlayerClient {

    private final RestClient restClient;

    public PlayerClient(RestClient playerRestClient) {
        this.restClient = playerRestClient;
    }

    public RiotAccountDto getPlayer(String gameName, String tagLine) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/v1/players/{gameName}/{tagLine}")
                            .build(gameName, tagLine))
                    .retrieve()
                    .body(RiotAccountDto.class);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new PlayerNotFoundException();
        } catch (ResourceAccessException exception) {
            throw new PlayerServiceUnavailableException();
        } catch (RestClientResponseException exception) {
            throw new PlayerServiceBadResponseException();
        }
    }
}

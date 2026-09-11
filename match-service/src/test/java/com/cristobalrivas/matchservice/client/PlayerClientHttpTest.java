package com.cristobalrivas.matchservice.client;

import com.cristobalrivas.matchservice.dto.RiotAccountDto;
import com.cristobalrivas.matchservice.error.PlayerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class PlayerClientHttpTest {

    @Test
    void readsPlayerFromPlayerServiceOverHttp() {
        RestClient.Builder builder = RestClient.builder().baseUrl("http://player-service");
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        PlayerClient playerClient = new PlayerClient(builder.build());

        server.expect(requestTo("http://player-service/api/v1/players/Player/LAN"))
                .andRespond(withSuccess("""
                        {"puuid":"puuid-1","gameName":"Player","tagLine":"LAN"}
                        """, MediaType.APPLICATION_JSON));

        RiotAccountDto result = playerClient.getPlayer("Player", "LAN");

        assertEquals("puuid-1", result.puuid());
        server.verify();
    }

    @Test
    void translatesPlayerServiceNotFound() {
        RestClient.Builder builder = RestClient.builder().baseUrl("http://player-service");
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        PlayerClient playerClient = new PlayerClient(builder.build());

        server.expect(requestTo("http://player-service/api/v1/players/Missing/LAN"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        assertThrows(PlayerNotFoundException.class,
                () -> playerClient.getPlayer("Missing", "LAN"));

        server.verify();
    }
}

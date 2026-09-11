package com.cristobalrivas.matchservice.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

class RiotSpectatorClientHttpTest {

    @Test
    void translatesRiotNotFoundToNoActiveMatch() {
        RestClient.Builder builder = RestClient.builder()
                .baseUrl("http://riot.example/la1");
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        RiotSpectatorClient client = new RiotSpectatorClient(
                builder,
                "http://riot.example/{region}", "test-key");

        server.expect(requestTo("http://riot.example/la1/lol/spectator/v5/active-games/by-summoner/puuid-1"))
                .andExpect(header("X-Riot-Token", "test-key"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        assertTrue(client.findActiveGame("la1", "puuid-1").isEmpty());
        server.verify();
    }

    @Test
    void translatesUnexpectedRiotResponseToBadResponse() {
        RestClient.Builder builder = RestClient.builder()
                .baseUrl("http://riot.example/la1");
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        RiotSpectatorClient client = new RiotSpectatorClient(
                builder,
                "http://riot.example/{region}", "test-key");

        server.expect(requestTo("http://riot.example/la1/lol/spectator/v5/active-games/by-summoner/puuid-1"))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(com.cristobalrivas.matchservice.error.RiotBadResponseException.class,
                () -> client.findActiveGame("la1", "puuid-1"));
        server.verify();
    }
}

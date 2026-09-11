package com.cristobalrivas.playerservice.controller;

import com.cristobalrivas.playerservice.dto.RiotAccountDto;
import com.cristobalrivas.playerservice.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlayerControllerTest {

    private final PlayerService playerService = mock(PlayerService.class);
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerService))
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void returnsPlayerAccount() throws Exception {
        when(playerService.getPlayer("Player", "LAN"))
                .thenReturn(new RiotAccountDto("puuid-1", "Player", "LAN"));

        mockMvc.perform(get("/api/v1/players/Player/LAN"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "puuid": "puuid-1",
                          "gameName": "Player",
                          "tagLine": "LAN"
                        }
                        """));
    }

    @Test
    void returnsNotFoundWhenRiotAccountDoesNotExist() throws Exception {
        when(playerService.getPlayer("Missing", "LAN"))
                .thenThrow(HttpClientErrorException.create(
                        HttpStatus.NOT_FOUND,
                        "Not Found",
                        HttpHeaders.EMPTY,
                        new byte[0],
                        StandardCharsets.UTF_8));

        mockMvc.perform(get("/api/v1/players/Missing/LAN"))
                .andExpect(status().isNotFound());
    }
}

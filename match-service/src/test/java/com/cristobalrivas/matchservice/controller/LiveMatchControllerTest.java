package com.cristobalrivas.matchservice.controller;

import com.cristobalrivas.matchservice.dto.CurrentGameInfoDto;
import com.cristobalrivas.matchservice.error.InvalidRegionException;
import com.cristobalrivas.matchservice.error.PlayerNotFoundException;
import com.cristobalrivas.matchservice.error.PlayerServiceUnavailableException;
import com.cristobalrivas.matchservice.error.RiotBadResponseException;
import com.cristobalrivas.matchservice.error.RiotUnavailableException;
import com.cristobalrivas.matchservice.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LiveMatchControllerTest {

    private final MatchService matchService = mock(MatchService.class);
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new LiveMatchController(matchService))
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void returnsOkWhenMatchExists() throws Exception {
        when(matchService.findLiveMatch("la1", "Player", "LAN"))
                .thenReturn(Optional.of(new CurrentGameInfoDto(1L, "CLASSIC", 120L, 2L, List.of())));

        mockMvc.perform(get("/api/v1/matches/live/la1/Player/LAN"))
                .andExpect(status().isOk());
    }

    @Test
    void returnsNoContentWhenThereIsNoActiveMatch() throws Exception {
        when(matchService.findLiveMatch("la1", "Player", "LAN"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/matches/live/la1/Player/LAN"))
                .andExpect(status().isNoContent());
    }

    @Test
    void returnsNotFoundWhenPlayerDoesNotExist() throws Exception {
        when(matchService.findLiveMatch("la1", "Missing", "LAN"))
                .thenThrow(new PlayerNotFoundException());

        mockMvc.perform(get("/api/v1/matches/live/la1/Missing/LAN"))
                .andExpect(status().isNotFound());
    }

    @Test
    void returnsBadRequestForInvalidRegion() throws Exception {
        when(matchService.findLiveMatch("la-1", "Player", "LAN"))
                .thenThrow(new InvalidRegionException("la-1"));

        mockMvc.perform(get("/api/v1/matches/live/la-1/Player/LAN"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returnsServiceUnavailableWhenPlayerServiceCannotBeReached() throws Exception {
        when(matchService.findLiveMatch("la1", "Player", "LAN"))
                .thenThrow(new PlayerServiceUnavailableException());

        mockMvc.perform(get("/api/v1/matches/live/la1/Player/LAN"))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    void returnsServiceUnavailableWhenRiotCannotBeReached() throws Exception {
        when(matchService.findLiveMatch("la1", "Player", "LAN"))
                .thenThrow(new RiotUnavailableException());

        mockMvc.perform(get("/api/v1/matches/live/la1/Player/LAN"))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    void returnsBadGatewayWhenRiotReturnsUnexpectedResponse() throws Exception {
        when(matchService.findLiveMatch("la1", "Player", "LAN"))
                .thenThrow(new RiotBadResponseException());

        mockMvc.perform(get("/api/v1/matches/live/la1/Player/LAN"))
                .andExpect(status().isBadGateway());
    }
}

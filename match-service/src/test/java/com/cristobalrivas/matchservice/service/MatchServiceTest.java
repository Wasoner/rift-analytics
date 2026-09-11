package com.cristobalrivas.matchservice.service;

import com.cristobalrivas.matchservice.client.PlayerClient;
import com.cristobalrivas.matchservice.client.RiotSpectatorClient;
import com.cristobalrivas.matchservice.dto.CurrentGameInfoDto;
import com.cristobalrivas.matchservice.dto.RiotAccountDto;
import com.cristobalrivas.matchservice.error.InvalidRegionException;
import com.cristobalrivas.matchservice.error.PlayerServiceUnavailableException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class MatchServiceTest {

    private final PlayerClient playerClient = mock(PlayerClient.class);
    private final RiotSpectatorClient spectatorClient = mock(RiotSpectatorClient.class);
    private final MatchService matchService = new MatchService(playerClient, spectatorClient);

    @Test
    void returnsActiveMatchUsingPlayerPuuid() {
        CurrentGameInfoDto game = new CurrentGameInfoDto(1L, "CLASSIC", 120L, 2L, java.util.List.of());
        when(playerClient.getPlayer("Player", "LAN"))
                .thenReturn(new RiotAccountDto("puuid-1", "Player", "LAN"));
        when(spectatorClient.findActiveGame("la1", "puuid-1"))
                .thenReturn(Optional.of(game));

        Optional<CurrentGameInfoDto> result = matchService.findLiveMatch("la1", "Player", "LAN");

        assertEquals(Optional.of(game), result);
        verify(spectatorClient).findActiveGame("la1", "puuid-1");
    }

    @Test
    void returnsEmptyWhenThereIsNoActiveMatch() {
        when(playerClient.getPlayer("Player", "LAN"))
                .thenReturn(new RiotAccountDto("puuid-1", "Player", "LAN"));
        when(spectatorClient.findActiveGame("la1", "puuid-1"))
                .thenReturn(Optional.empty());

        Optional<CurrentGameInfoDto> result = matchService.findLiveMatch("la1", "Player", "LAN");

        assertEquals(Optional.empty(), result);
    }

    @Test
    void rejectsInvalidRegionBeforeCallingDependencies() {
        assertThrows(InvalidRegionException.class,
                () -> matchService.findLiveMatch("la-1", "Player", "LAN"));

        verifyNoInteractions(playerClient, spectatorClient);
    }

    @Test
    void propagatesPlayerServiceCommunicationFailure() {
        when(playerClient.getPlayer("Player", "LAN"))
                .thenThrow(new PlayerServiceUnavailableException());

        assertThrows(PlayerServiceUnavailableException.class,
                () -> matchService.findLiveMatch("la1", "Player", "LAN"));
    }
}

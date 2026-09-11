package com.cristobalrivas.playerservice.service;

import com.cristobalrivas.playerservice.dto.RiotAccountDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlayerServiceTest {

    private final RiotAccountClient riotAccountClient = mock(RiotAccountClient.class);
    private final PlayerService playerService = new PlayerService(riotAccountClient);

    @Test
    void returnsAccountFromRiotClient() {
        RiotAccountDto account = new RiotAccountDto("puuid-1", "Player", "LAN");
        when(riotAccountClient.getAccountByRiotId("Player", "LAN")).thenReturn(account);

        RiotAccountDto result = playerService.getPlayer("Player", "LAN");

        assertSame(account, result);
        verify(riotAccountClient).getAccountByRiotId("Player", "LAN");
    }
}

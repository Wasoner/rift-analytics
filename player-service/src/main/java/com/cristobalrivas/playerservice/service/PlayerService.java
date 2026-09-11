package com.cristobalrivas.playerservice.service;

import com.cristobalrivas.playerservice.dto.RiotAccountDto;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final RiotAccountClient riotAccountClient;

    public PlayerService(RiotAccountClient riotAccountClient) {
        this.riotAccountClient = riotAccountClient;
    }

    public RiotAccountDto getPlayer(String gameName, String tagLine) {
        return riotAccountClient.getAccountByRiotId(gameName, tagLine);
    }
}

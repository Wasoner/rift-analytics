package com.cristobalrivas.matchservice.service;

import com.cristobalrivas.matchservice.client.PlayerClient;
import com.cristobalrivas.matchservice.client.RiotSpectatorClient;
import com.cristobalrivas.matchservice.dto.CurrentGameInfoDto;
import com.cristobalrivas.matchservice.dto.RiotAccountDto;
import com.cristobalrivas.matchservice.error.InvalidRegionException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchService {

    private final PlayerClient playerClient;
    private final RiotSpectatorClient riotSpectatorClient;

    public MatchService(PlayerClient playerClient, RiotSpectatorClient riotSpectatorClient) {
        this.playerClient = playerClient;
        this.riotSpectatorClient = riotSpectatorClient;
    }

    public Optional<CurrentGameInfoDto> findLiveMatch(
            String region,
            String gameName,
            String tagLine) {
        validateRegion(region);
        RiotAccountDto account = playerClient.getPlayer(gameName, tagLine);
        return riotSpectatorClient.findActiveGame(region, account.puuid());
    }

    private void validateRegion(String region) {
        if (region == null || !region.matches("[a-zA-Z0-9]+")) {
            throw new InvalidRegionException(region);
        }
    }
}
